package com.example.treasurehunter.model.dto;

import com.example.treasurehunter.model.Position;
import java.util.Set;

public class GameStateResponse {
    private String userId;
    private String playerName;
    private Set<String> revealed;
    private int turnCount;
    private boolean completed;
    private int foundCount;

    public GameStateResponse() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Set<String> getRevealed() {
        return revealed;
    }

    public void setRevealed(Set<String> revealed) {
        this.revealed = revealed;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getFoundCount() {
        return foundCount;
    }

    public void setFoundCount(int foundCount) {
        this.foundCount = foundCount;
    }
}
