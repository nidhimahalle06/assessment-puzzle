package com.example.treasurehunter.model.dto;

public class StartGameRequest {
    private String playerName;

    public StartGameRequest() {}

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
