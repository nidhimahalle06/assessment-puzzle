package com.example.treasurehunter.model.dto;

import com.example.treasurehunter.model.CellResult;
import java.util.List;

public class PlayResponse {
    private List<CellResult> results;
    private int totalTurns;
    private boolean gameCompleted;

    public PlayResponse() {}

    public PlayResponse(List<CellResult> results, int totalTurns, boolean gameCompleted) {
        this.results = results;
        this.totalTurns = totalTurns;
        this.gameCompleted = gameCompleted;
    }

    public List<CellResult> getResults() {
        return results;
    }

    public void setResults(List<CellResult> results) {
        this.results = results;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public void setTotalTurns(int totalTurns) {
        this.totalTurns = totalTurns;
    }

    public boolean isGameCompleted() {
        return gameCompleted;
    }

    public void setGameCompleted(boolean gameCompleted) {
        this.gameCompleted = gameCompleted;
    }
}
