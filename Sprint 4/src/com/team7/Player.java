package com.team7;

public class Player 
{
    int id;
    String username;
    boolean team;
    int score;
    
    String displayName;

    Player(int id, String username, boolean team)
    {
        this.id = id;
        this.username = username;
        this.team = team;
        this.score = 0;
        displayName = username + " (" + id + ")";
    }

    void playerScored(int points)
    {
        score += points;
    }
}
