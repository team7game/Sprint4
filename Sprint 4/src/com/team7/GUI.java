package com.team7;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.JScrollBar;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.*;
import java.util.List;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;


public class GUI extends JFrame {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    int titleSize = (int)screenSize.getHeight()/30;
    int headerSize = (int)screenSize.getHeight()/40;
    int bodySize = (int)screenSize.getHeight()/50;

    Font timerFont = new Font("Tahoma", Font.BOLD, titleSize*3/2);
    Font titleFont = new Font("Tahoma", Font.BOLD, titleSize);
    Font headerFont = new Font("Tahoma", Font.PLAIN, headerSize);
    Font bodyFont = new Font("Tahoma", Font.PLAIN, bodySize);
    Font actionFont = new Font("Tahoma", Font.BOLD, bodySize);
    Font lineSpace = new Font("Tahoma", Font.PLAIN, bodySize/10);

    Font myFont = new Font("Arial", Font.BOLD, 18);

    Color redTeamColor = new Color(255, 36, 0);
    Color greenTeamColor = new Color(57, 255, 20);
    Color whiteTextColor = new Color(255, 255, 255);
    
    JPanel mainPanel;
    JPanel entryCardPanel;

    PlayerEntryScreen pes;
    GameActionScreen gas;
    CardLayout cl;
    
    public GUI() 
    {
        super("Photon");
        mainPanel = new JPanel();
        //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new BorderLayout());

        cl = new CardLayout();
        entryCardPanel = new JPanel(cl);

        pes = new PlayerEntryScreen();

        gas = new GameActionScreen();
        mainPanel.add(entryCardPanel);
        //mainPanel.add(bp);

        entryCardPanel.add(pes);
        entryCardPanel.add(gas);

        pes.setOpaque(true);

        setSize(screenSize.width, screenSize.height);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel); 
        //pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setVisible(true);  
    }

    class PlayerEntryScreen extends JPanel{

        final int ROW_LIMIT = 10;

        JTextField idField;
        JTextField usernameField;

        JButton submitID;
        JButton submitUsername;

        ButtonGroup group;
        JRadioButton redTeamButton;
        JRadioButton greenTeamButton;

	    JTable redTeamTable;
	    JTable greenTeamTable;

        DefaultTableModel redTeamModel;
        DefaultTableModel greenTeamModel;

        JPanel entryPanel;
        CardLayout entryCardLayout;
	    
        JPanel countdownPanel;
        CardLayout countdownLayout;
        JLabel countdownLabel;

        PlayerEntryScreen()
        {
            setLayout(new BorderLayout(0, screenSize.height/20));
            setBackground(Color.BLACK);

            entryCardLayout = new CardLayout();
            group = new ButtonGroup();

            entryPanel = new JPanel();
            add(entryPanel, "North");
            entryPanel.setLayout(entryCardLayout);
            
            JPanel idPanel = new JPanel();
            idPanel.setBackground(Color.BLACK);
            entryPanel.add(idPanel, "name_275215514144300");
            idPanel.setLayout(new FlowLayout(FlowLayout.CENTER, screenSize.width/40, screenSize.height/40));

            JLabel idLabel = new JLabel("ID: ");
            idLabel.setFont(headerFont);
            idLabel.setForeground(Color.WHITE);
            idLabel.setBackground(Color.BLACK);
            idPanel.add(idLabel);

            idField = new JTextField();
            idField.setFont(headerFont);
            idPanel.add(idField);
            idField.setColumns(screenSize.width/200);   //length of text field
            
            redTeamButton = new JRadioButton("Red", true);
            redTeamButton.setFont(headerFont);
            redTeamButton.setForeground(Color.RED);
            redTeamButton.setBackground(Color.BLACK);
            idPanel.add(redTeamButton);
            group.add(redTeamButton);
            
            greenTeamButton = new JRadioButton("Green");
            greenTeamButton.setFont(headerFont);
            greenTeamButton.setForeground(Color.GREEN);
            greenTeamButton.setBackground(Color.BLACK);
            idPanel.add(greenTeamButton);
            group.add(greenTeamButton);
            
            submitID= new JButton("Submit");
            //submitID.setBackground(Color.BLACK);
            //submitID.setForeground(redTeamColor);
            //submitID.setBorder(BorderFactory.createLineBorder(whiteTextColor));
            submitID.setFocusable(false);
            submitID.setFont(headerFont);
            idPanel.add(submitID);
            
            JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, screenSize.width/40, screenSize.height/40));
            usernamePanel.setBackground(Color.BLACK);
            entryPanel.add(usernamePanel, "name_275236543660799");

            JLabel usernameLabel = new JLabel("Username: ");
            usernameLabel.setFont(headerFont);
            usernameLabel.setBackground(Color.BLACK);
            usernameLabel.setForeground(Color.WHITE);
            usernamePanel.add(usernameLabel);
            
            usernameField = new JTextField();
            usernameField.setFont(headerFont);
            usernamePanel.add(usernameField);
            usernameField.setColumns(screenSize.width/150);       //length of text field
            
            submitUsername = new JButton("Submit");
            submitUsername.setFocusable(false);
            submitUsername.setFont(headerFont);
            usernamePanel.add(submitUsername);
            
            JPanel displayPanel = new JPanel();
            displayPanel.setBackground(Color.BLACK);
            add(displayPanel, "Center");
            displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.X_AXIS));
            
            JPanel redTeamPanel = new JPanel(new BorderLayout());
            redTeamPanel.setBackground(Color.BLACK);
            displayPanel.add(redTeamPanel);
            
            JLabel redTeamLabel = new JLabel("Red Team");
            redTeamLabel.setFont(titleFont);
            redTeamLabel.setForeground(Color.RED);
            redTeamLabel.setBackground(Color.BLACK);
            redTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
            redTeamPanel.add(redTeamLabel, "North");
            
            redTeamTable = new JTable();

            redTeamModel = new DefaultTableModel(0, 2){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            redTeamTable.setModel(redTeamModel);
            setTableLook(redTeamTable, Color.BLACK, Color.RED);
            redTeamPanel.add(redTeamTable, "Center");
            
            JPanel greenTeamPanel = new JPanel(new BorderLayout());
            greenTeamPanel.setBackground(Color.BLACK);
            displayPanel.add(greenTeamPanel);
            
            JLabel greenTeamLabel = new JLabel("Green Team");
            greenTeamLabel.setFont(titleFont);
            greenTeamLabel.setForeground(Color.GREEN);
            greenTeamLabel.setBackground(Color.BLACK);
            greenTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
            greenTeamPanel.add(greenTeamLabel, "North");
            
            greenTeamTable = new JTable();
    
            greenTeamModel = new DefaultTableModel(0, 2){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            greenTeamTable.setModel(greenTeamModel);
            setTableLook(greenTeamTable, Color.BLACK, Color.GREEN);
            greenTeamPanel.add(greenTeamTable, "Center");

            countdownPanel = new JPanel();
            countdownLayout = new CardLayout();
            countdownLabel = new JLabel("Press the 'S' key to start the game");
            countdownLabel.setFont(headerFont);
            countdownLabel.setForeground(Color.WHITE);
            countdownLabel.setBackground(Color.BLACK);
            countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
            countdownPanel.add(countdownLabel);
            countdownPanel.setLayout(countdownLayout);
            countdownPanel.setBackground(Color.BLACK);
            countdownLayout.setVgap(20);

            add(countdownPanel, "South");
            
        }

        void setTableLook(JTable table, Color bg, Color fg)
        {
            table.setRowHeight(headerSize*2);
            table.setFont(headerFont);
            table.setFocusable(false);
            table.setCellSelectionEnabled(false);
            table.setShowGrid(false);
            table.setBackground(bg);
            table.setForeground(fg);
            table.setFillsViewportHeight(true);
            
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            
            for(int j = 0; j < table.getColumnCount(); j++)
            {
                //table.getColumnModel().getColumn(j).setCellRenderer(new CustomCellRenderer(bg, fg));
                table.getColumnModel().getColumn(j).setCellRenderer( centerRenderer );
            }
        }

        public void addPlayerToTable(Player player)
        {
            if(player.team)
            {   
                if(redTeamTable.getRowCount() < ROW_LIMIT)
                {
                    //DefaultTableModel model = (DefaultTableModel) redTable.getModel();
                    redTeamModel.addRow(new Object[]{player.id, player.username});
                    //redPlayers.add(new Object[]{id, username});
                    //redTeamCount++;
                }           
            }
            else
            {
                if(greenTeamTable.getRowCount() < ROW_LIMIT)
                {
                    //DefaultTableModel model = (DefaultTableModel) greenTable.getModel();
                    greenTeamModel.addRow(new Object[]{player.id, player.username});
                    //greenPlayers.add(new Object[]{id, username});
                    //greenTeamCount++;
                }
            }
        }

    }

    class GameActionScreen extends JPanel {

        //could use component listener to dynamically change font size as window is resized

        JLabel redTeamLabel;
        JLabel greenTeamLabel;
        JLabel timerLabel;

        JTable redScoreboard;
        JTable greenScoreboard;

        DefaultTableModel redModel;
        DefaultTableModel greenModel;

        DefaultTableCellRenderer defaultRenderer;

        JTextPane actionText;

        float currentTime;

        GameActionScreen()
        {
            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(Color.BLACK);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JPanel timerPanel = new JPanel();
            FlowLayout flowLayout = (FlowLayout) timerPanel.getLayout();
            flowLayout.setHgap(10);
            flowLayout.setVgap(20);
            flowLayout.setAlignment(FlowLayout.CENTER);
            timerPanel.setBackground(Color.BLACK);
            mainPanel.add(timerPanel);
            
            timerLabel = new JLabel("Time Remaining: 0:37");
            timerLabel.setForeground(Color.WHITE);
            timerLabel.setFont(timerFont);
            timerPanel.add(timerLabel);
            
            
            JPanel teamPanel = new JPanel();
            BoxLayout teamLayout = new BoxLayout(teamPanel, BoxLayout.X_AXIS);
            mainPanel.add(teamPanel);
            teamPanel.setLayout(teamLayout);
            
            BorderLayout redTeamLayout = new BorderLayout();
            redTeamLayout.setVgap(20);

            JPanel redTeamPanel = new JPanel(redTeamLayout);
            redTeamPanel.setBackground(Color.BLACK);
            
            teamPanel.add(redTeamPanel);
            
            redTeamLabel = new JLabel("RED TEAM     " + 0);
            redTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
            redTeamLabel.setFont(titleFont);
            redTeamLabel.setForeground(redTeamColor);
            redTeamPanel.add(redTeamLabel, "North");
            
            redScoreboard = new JTable();
            redModel = new DefaultTableModel(0, 2)
            {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            redScoreboard.setModel(redModel);
            setTableLook(redScoreboard, Color.BLACK, Color.WHITE);
            redTeamPanel.add(redScoreboard, "Center");

            BorderLayout greenTeamLayout = new BorderLayout();
            greenTeamLayout.setVgap(20);
            
            JPanel greenTeamPanel = new JPanel(greenTeamLayout);
            greenTeamPanel.setBackground(Color.BLACK);
            teamPanel.add(greenTeamPanel);
            
            greenTeamLabel = new JLabel("GREEN TEAM     " + 0);
            greenTeamLabel.setFont(titleFont);
            greenTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
            greenTeamLabel.setForeground(greenTeamColor);
            greenTeamPanel.add(greenTeamLabel, "North");
            
            greenScoreboard = new JTable();
            greenModel = new DefaultTableModel(0, 2)
            {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            greenScoreboard.setModel(greenModel);
            setTableLook(greenScoreboard, Color.BLACK, Color.WHITE);
            greenTeamPanel.add(greenScoreboard, "Center");
            
            JPanel actionPanel = new JPanel();
            mainPanel.add(actionPanel);
            actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

            actionText = new JTextPane();
            actionText.setFont(actionFont);
            actionText.setForeground(whiteTextColor);
            actionText.setBackground(Color.BLACK);
            actionText.setEditable(false);
            actionText.setBorder(BorderFactory.createLineBorder(whiteTextColor));
            actionText.setPreferredSize(new Dimension(screenSize.width, screenSize.height/2));
            actionText.setMaximumSize(new Dimension(screenSize.width, screenSize.height/2));

            JScrollPane actionScroll = new JScrollPane(actionText);
            actionScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            actionScroll.setPreferredSize(new Dimension(screenSize.width, screenSize.height/2));
            actionScroll.setMaximumSize(new Dimension(screenSize.width, screenSize.height/2));
            actionScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
                public void adjustmentValueChanged(AdjustmentEvent e) {  

                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
                }
            });
            
            actionPanel.setBackground(Color.BLACK);
            //actionPanel.setBorder(BorderFactory.createLineBorder(whiteTextColor));
            //actionPanel.add(actionText);
            //actionPanel.add(actionScroll);
            mainPanel.add(actionScroll);
            
            

            setLayout(new BorderLayout());
            add(mainPanel);
            pack();
        }

        //Custom cell renderer that allows us to change the look of the default table
        class ChangeCellColor extends DefaultTableCellRenderer {

            int changeRow;
            Color rowColor;

            public ChangeCellColor(Color rowColor, int row)
            {
                changeRow = row;
                this.rowColor = rowColor;
            }

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
            {
      
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if(row == changeRow)
                {
                    cell.setForeground(rowColor);
                }
        
                return cell;
            }
      
        }

        void flashScore(boolean winningTeam)
        {
            if(winningTeam)
            {
                if(redTeamLabel.getForeground() == redTeamColor)
                {
                    redTeamLabel.setForeground(Color.BLACK);
                }
                else
                {
                    redTeamLabel.setForeground(redTeamColor);
                }
            }
            else
            {
                if(greenTeamLabel.getForeground() == greenTeamColor)
                {
                    greenTeamLabel.setForeground(Color.BLACK);
                }
                else
                {
                    greenTeamLabel.setForeground(greenTeamColor);
                }
            }
        }

        
        public void nameFlash(Player shooter, Player target, float flashDuration)
        {
            Timer timer = new Timer();

            if(shooter.team)
            {
                for(int i = 0; i < redScoreboard.getRowCount(); i++)
                {
                    if(redScoreboard.getValueAt(i, 0) == shooter.displayName)
                    {
                        redScoreboard.getColumnModel().getColumn(0).setCellRenderer(new ChangeCellColor(redTeamColor, i));
                    }
                }
                for(int j = 0; j < greenScoreboard.getRowCount(); j++)
                {
                    if(greenScoreboard.getValueAt(j, 0) == target.displayName)
                    {
                        greenScoreboard.getColumnModel().getColumn(0).setCellRenderer(new ChangeCellColor(redTeamColor, j));
                    }
                }

                
            }
            else
            {
                for(int i = 0; i < redScoreboard.getRowCount(); i++)
                {
                    if(redScoreboard.getValueAt(i, 0) == target.displayName)
                    {
                        redScoreboard.getColumnModel().getColumn(0).setCellRenderer(new ChangeCellColor(greenTeamColor, i));
                    }
                }
                for(int j = 0; j < greenScoreboard.getRowCount(); j++)
                {
                    if(greenScoreboard.getValueAt(j, 0) == shooter.displayName)
                    {
                        greenScoreboard.getColumnModel().getColumn(0).setCellRenderer(new ChangeCellColor(greenTeamColor, j));
                    }
                }
            }

            timer.scheduleAtFixedRate(new TimerTask() {

                public void run() {

                    redScoreboard.getColumnModel().getColumn(0).setCellRenderer(defaultRenderer);
                    greenScoreboard.getColumnModel().getColumn(0).setCellRenderer(defaultRenderer);
                    
                    timer.cancel();
                }

            }, 100, 1);
        }
        
        

        void setTableLook(JTable table, Color bg, Color fg)
        {
            table.setFont(new Font("Tahoma", Font.PLAIN, bodySize));
            table.setRowHeight(bodySize*2);
            table.setFocusable(false);
            table.setCellSelectionEnabled(false);
            table.setShowGrid(false);
            table.setBackground(bg);
            table.setForeground(fg);
            table.setFillsViewportHeight(true);

            /*
            DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

            leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
            rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

            table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
            table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
            */
            
            
            defaultRenderer = new DefaultTableCellRenderer();
            defaultRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            defaultRenderer.setForeground(whiteTextColor);
            
            
            for(int j = 0; j < table.getColumnCount(); j++)
            {
                //table.getColumnModel().getColumn(j).setCellRenderer(new CustomCellRenderer(bg, fg));
                table.getColumnModel().getColumn(j).setCellRenderer(defaultRenderer);
            }
            
        }

        public void addPlayersToScoreboard(List<Player> players)
        {
            for(int i = 0; i < players.size(); i++)
            {
                if(players.get(i).team)
                {
                    //redModel.addRow(new Object[]{players.get(i).username + " (" + players.get(i).id + ")", players.get(i).score});
                    redModel.addRow(new Object[]{players.get(i).displayName, players.get(i).score});
                }
                else
                {
                    //greenModel.addRow(new Object[]{players.get(i).username + " (" + players.get(i).id + ")", players.get(i).score});
                    greenModel.addRow(new Object[]{players.get(i).displayName, players.get(i).score});
                }
            }
        }

        public void updateTeamScores(int redTeamScore, int greenTeamScore)
        {
            redTeamLabel.setText("RED TEAM          " + redTeamScore);
            greenTeamLabel.setText("GREEN TEAM          " + greenTeamScore);
        }

    }
}