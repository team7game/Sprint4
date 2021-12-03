package com.team7;

import java.awt.event.*;

import javax.swing.JRadioButton;


public class EventHandler implements KeyListener, ActionListener, WindowListener
{
    JRadioButton redButton;
    JRadioButton greenButton;

    GUI gui;
    Database database;
    GameManager manager;

    int userID;

    boolean team; //red = true, green = false

    boolean gameAction;

    int redTeamCount = 0;
    int greenTeamCount = 0;
    
    public EventHandler(GUI g, Database db, GameManager gm)
    {
        database = db;
        gui = g;
        manager = gm;
        gui.addKeyListener(this);
        gui.addWindowListener(this);

        gui.pes.submitID.addActionListener(this);
        redButton = gui.pes.redTeamButton;
        redButton.addActionListener(this);

        gui.pes.submitUsername.addActionListener(this);
        greenButton = gui.pes.greenTeamButton;
        greenButton.addActionListener(this);

        gameAction = false;
    }

    @Override
    public void keyPressed(KeyEvent event) 
    {
    
    }

    @Override
    public void keyReleased(KeyEvent event) 
    {

    }

    @Override
    public void keyTyped(KeyEvent event) 
    {
        //System.out.println("Key Typed, " + gameAction);

        if((event.getKeyChar() == KeyEvent.VK_F5 || event.getKeyChar() == 's') && gameAction == false)
        {
            //TEMPORARY
            gameAction = true;
            manager.startCountdownTimer();
        }
    }    

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("action performed");

        if(gui.pes.greenTeamButton.isSelected())
        {
            team = false;
            //gui.pes.submitID.setForeground(gui.greenTeamColor);
        }
        else
        {
            team = true;
            //gui.pes.submitID.setForeground(gui.redTeamColor);
        }

        if(e.getSource() == gui.pes.submitID)
        {
            System.out.println("Submitted ID");

            try
            {
                userID = Integer.parseInt(gui.pes.idField.getText());

                /*
                if(gui.pes.greenTeamButton.isSelected())
                {
                    team = false;
                    gui.pes.submitID.setForeground(gui.greenTeamColor);
                }
                else
                {
                    team = true;
                    gui.pes.submitID.setForeground(gui.redTeamColor);
                }
                */

                database.connect();

                if(database.checkID(userID))
                {
                    System.out.println("Player exists");
                    Player p = manager.createPlayer(userID, database.getName(userID), team);
                    gui.pes.addPlayerToTable(p);
                }
                else
                {
                    System.out.println("Player does not exist");
                    gui.pes.entryCardLayout.next(gui.pes.entryPanel);
                }

                database.close();
            }
            catch(NumberFormatException exception)
            {
                gui.pes.idField.setText("Invalid Input");
            }

            //Clears text after submission
            gui.pes.idField.setText("");
        }

        if(e.getSource() == gui.pes.submitUsername)
        {
            if(gui.pes.usernameField.getText() != null)
            {
                database.connect();
                System.out.println("Created player. ID: " + userID + " Username: " + gui.pes.usernameField.getText());
                Player p = manager.createPlayer(userID, gui.pes.usernameField.getText(), team);
                gui.pes.addPlayerToTable(p);
                database.createPlayer(userID, gui.pes.usernameField.getText());

                gui.pes.entryCardLayout.next(gui.pes.entryPanel);
                database.close();
            }

            //Clears text after submission
            gui.pes.usernameField.setText("");
        }

        gui.requestFocus();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        System.out.println("Window closed");

        if(manager.trafficGenerator != null)
        {
            manager.trafficGenerator.destroy();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }
}






