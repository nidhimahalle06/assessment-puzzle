package com.example.treasurehunter.model.dto;

import com.example.treasurehunter.model.Position;
import java.util.List;

public class PlayRequest {
    private String userId;
    private List<Position> positions;

    public PlayRequest() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
