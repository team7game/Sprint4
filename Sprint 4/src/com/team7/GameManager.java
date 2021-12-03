package com.team7;

import java.util.*;

import javax.swing.text.StyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.Style;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.JTextPane;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class GameManager {

    final int hitValue = 100;

    final int eventCount = 1000;
    
    List<Player> players = new ArrayList<Player>();
    
    int redTeamScore = 0;
    int greenTeamScore = 0;

    int gameTimerDuration = 360;
    int currentGameTime;

    int countdownTimerDuration = 10;
    int currentCountdownTime;

    int currentActionLine = 0;
    int maxActionLines = 5;
    
    GUI gui;

    Process trafficGenerator;

    public static boolean gameStarted = false;
    

    GameManager(GUI g)
    {
        gui = g;
    }

    public Player createPlayer(int id, String username, boolean team)
    {
        Player player = new Player(id, username, team);
        players.add(player);
        return player;
    }

    void startGame() throws IOException, SocketException
    {   
        gameStarted = true;
        //gui.gas.setActionText("");
        gui.cl.next(gui.entryCardPanel);
        gui.gas.addPlayersToScoreboard(players);
        startGameTimer();

        DatagramSocket ds = new DatagramSocket(7501);
	    byte[] receive = new byte[65535];

	    DatagramPacket DpReceive = null;

        //command += players.get(0).id + " " + players.get(1).id + " " + players.get(2).id + " " + players.get(3).id + " " + eventCount;
        System.out.println(generateCommand(eventCount));

        trafficGenerator = Runtime.getRuntime().exec(generateCommand(eventCount));

        while(true)
        {
            System.out.println("Starting to receive data...");
            // Step 2 : create a DatgramPacket to receive the data.
			DpReceive = new DatagramPacket(receive, receive.length);

			// Step 3 : revieve the data in byte buffer.
			ds.receive(DpReceive);

			// Exit the server if the client sends "bye"
			if (data(receive).toString().equals("bye"))
			{
				System.out.println("Client sent bye.....EXITING");
				break;
			}
            else
            {
                System.out.println("Client:-" + data(receive));
                hitPlayer(data(receive).toString());
            }

			// Clear the buffer after every message.
			receive = new byte[65535];
        }
    }

    void hitPlayer(String message)
    {
        System.out.println("Player hit!");

        String[] split = message.split(":");

        int shooterID = Integer.parseInt(split[0]);
        int targetID = Integer.parseInt(split[1]);

        Player shooter = null;
        Player target = null;

        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).id == shooterID)
            {
                shooter = players.get(i);
            }
            else if(players.get(i).id == targetID)
            {
                target = players.get(i);
            }
        }
        
        //gui.gas.nameFlash(shooter, target, 0.5f);
        shooter.score += 100;
        target.score -= 100;
        updateScoreboard();
        displayAction(shooter, target);
    }

    void displayAction(Player shooter, Player target)
    {
        StyledDocument doc = gui.gas.actionText.getStyledDocument();
        Style style = gui.gas.actionText.addStyle("I'm a Style", null);

        changeLineSpacing(gui.gas.actionText, 0.5f, false);

        if(shooter.team)
        {
            StyleConstants.setForeground(style, gui.redTeamColor);
            try { doc.insertString(doc.getLength(), "   " + shooter.username + " (" + shooter.id + ")",style); }
            catch (BadLocationException e){}

            StyleConstants.setForeground(style, gui.whiteTextColor);
            try { doc.insertString(doc.getLength(), "     hit     ", style); }
            catch (BadLocationException e){}

            StyleConstants.setForeground(style, gui.greenTeamColor);
            try { doc.insertString(doc.getLength(), target.username + " (" + target.id + ")\n", style); }
            catch (BadLocationException e){}
        }
        else
        {
            StyleConstants.setForeground(style, gui.greenTeamColor);
            try { doc.insertString(doc.getLength(), "   " + shooter.username + " (" + shooter.id + ")",style); }
            catch (BadLocationException e){}

            StyleConstants.setForeground(style, gui.whiteTextColor);
            try { doc.insertString(doc.getLength(), "     hit     ", style); }
            catch (BadLocationException e){}

            StyleConstants.setForeground(style, gui.redTeamColor);
            try { doc.insertString(doc.getLength(), target.username + " (" + target.id + ")\n", style); }
            catch (BadLocationException e){}
        }

    }

    void updateScoreboard()
    {
        redTeamScore = 0;
        greenTeamScore = 0;

        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).team)
            {
                redTeamScore += players.get(i).score;
            }
            else
            {
                greenTeamScore += players.get(i).score;
            }
        }

        gui.gas.updateTeamScores(redTeamScore, greenTeamScore);

        //Clears tables and updates them with new player scores
        gui.gas.redModel.setRowCount(0);
        gui.gas.greenModel.setRowCount(0);
        gui.gas.addPlayersToScoreboard(players);
    }

    public void startCountdownTimer()
    {
        gui.pes.countdownLayout.next(gui.pes.countdownPanel);

        Timer countdownTimer = new Timer();
        currentCountdownTime = countdownTimerDuration;

        countdownTimer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                currentCountdownTime--;

                gui.pes.countdownLabel.setText("Game starting in " + currentCountdownTime);
        
                if (currentCountdownTime <= 0) {
                    countdownTimer.cancel();

                    try
                    {
                        startGame();
                    }
                    catch(IOException e)
                    {
                        System.out.println("Error starting game. Info: " + e.getMessage());
                    }
                    
                }
            }

        }, 0, 1000);
    }

    public void startGameTimer()
    {
        Timer timer = new Timer();
        currentGameTime = gameTimerDuration;

            timer.scheduleAtFixedRate(new TimerTask() {

                public void run() {
                    
                    String minutes = (currentGameTime/60) + "";
                    String seconds = "";
                    
                    if(currentGameTime%60 < 10)
                    {
                        seconds = "0" + currentGameTime%60;
                    }
                    else
                    {
                        seconds = "" + currentGameTime%60;
                    }

                    String displayTime = minutes + ":" + seconds;
                    gui.gas.timerLabel.setText(displayTime);
                    currentGameTime--;
            
                    if (currentGameTime == gameTimerDuration/2){
                        gui.gas.timerLabel.setText("Warning: Half the time remaining");
                    }
            
                    if (currentGameTime == 3){
                        gui.gas.timerLabel.setText("Warning: 3 seconds remaining");
                    }
            
                    if (currentGameTime <= 0) {
                        timer.cancel();
                        gui.gas.timerLabel.setText("Game Over");
                        trafficGenerator.destroy();
                        //gameAction = false
                    }
                }

            }, 0, 1000);

            Timer flashTimer = new Timer();

            flashTimer.scheduleAtFixedRate(new TimerTask() {
                
                public void run()
                {
                    if(redTeamScore > greenTeamScore)
                    {
                        gui.gas.flashScore(true);
                    }
                    else if(greenTeamScore > redTeamScore)
                    {
                        gui.gas.flashScore(false);
                    }
                    else 
                    {
                        gui.gas.redTeamLabel.setForeground(gui.redTeamColor);
                        gui.gas.greenTeamLabel.setForeground(gui.greenTeamColor);
                    }
                }

            }, 0, 500);
    }

    String generateCommand(int events)
    {
        String command = "python python_trafficgenerator.py ";

        List<Integer> params = new ArrayList<Integer>();
        
        for(int i = 0; i < players.size(); i++)
        {
            params.add(players.get(i).id);
        }
        
        params.add(events);

        for(int j = 0; j < params.size(); j++)
        {
            String p = params.get(j) + " ";
            command += p;
        }

        return command;
    }

    private void changeLineSpacing(JTextPane pane, float factor, boolean replace) {

        pane.selectAll();
        SimpleAttributeSet set = new SimpleAttributeSet(pane.getParagraphAttributes());
        StyleConstants.setLineSpacing(set, factor);
        pane.setParagraphAttributes(set, replace);

    }

    public static StringBuilder data(byte[] a)
	{
		if (a == null)
			return null;
		StringBuilder ret = new StringBuilder();
		int i = 0;
		while (a[i] != 0)
		{
			ret.append((char) a[i]);
			i++;
		}
		return ret;
	}

}
