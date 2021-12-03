
package com.team7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author aschm
 */
public class Database {
    private final String url = "jdbc:postgresql://ec2-23-20-208-173.compute-1.amazonaws.com:5432/d7bpqm6drl9ed";
    private final String user = "vqtjdaxblwljil";
    private final String password = "ecffcd187e4586069f6dbf12135d504a717840d8301e39159c08cbb2a1c9ce08";
    
    Connection conn = null;
    
    public Connection connect() {
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database successfully");
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
    
    public void command(String comm) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = comm;
            stmt.executeUpdate(sql);
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean checkID(int identifier) {
        boolean present = false;
        Statement stmt = null;
        
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from player");
            while(rs.next()) {
                int id = rs.getInt("id");
                if (id == identifier) {
                    return true;
                }
            }

            //stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return present;
    }
    
    public void createPlayer(int identifier, String name) {
        Statement stmt = null;
        
        try {
            stmt = conn.createStatement();
            String sql = "insert into player(id, first_name, last_name, codename) "
                    + "values(" + identifier +"," + "'first_name', 'last_name', '" + name + "');";
            command(sql);
            //stmt.close();
        }
        catch (SQLException e) {
            System.out.println("Player creation failed." + e.getMessage());
        }
    }
    
    public String getName(int identifier) {
        String name = "";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from player");
            while(rs.next()) {
                int id = rs.getInt("id");
                if (id == identifier) {
                    name = rs.getString("codename");
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return name;
    }
    
    public void removePlayer(int identifier) {
        Statement stmt = null;
        
        try {
            stmt = conn.createStatement();
            String sql = "delete from player where id = " + identifier;
            command(sql);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void close() {
        try {
            conn.close();
            System.out.println("Connection closed successfully");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
