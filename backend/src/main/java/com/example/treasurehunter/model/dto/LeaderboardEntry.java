package com.example.treasurehunter.model.dto;

public class LeaderboardEntry {
    private String playerName;
    private int turns;

    public LeaderboardEntry() {}

    public LeaderboardEntry(String playerName, int turns) {
        this.playerName = playerName;
        this.turns = turns;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }
}
