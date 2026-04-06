package com.example.treasurehunter.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private String userId;
    private String playerName;
    private List<Position> treasures = new ArrayList<>();
    private Set<String> revealed = new HashSet<>();
    private int turnCount = 0;
    private boolean completed = false;
    private int foundCount = 0;

    public Game() {}

    public Game(String userId, String playerName, List<Position> treasures) {
        this.userId = userId;
        this.playerName = playerName;
        this.treasures = treasures;
    }

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

    public List<Position> getTreasures() {
        return treasures;
    }

    public void setTreasures(List<Position> treasures) {
        this.treasures = treasures;
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
