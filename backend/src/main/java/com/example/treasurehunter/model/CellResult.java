package com.example.treasurehunter.model;

public class CellResult {
    private Position position;
    private boolean isTreasure;
    private Integer distance; // null when treasure

    public CellResult() {}

    public CellResult(Position position, boolean isTreasure, Integer distance) {
        this.position = position;
        this.isTreasure = isTreasure;
        this.distance = distance;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isTreasure() {
        return isTreasure;
    }

    public void setTreasure(boolean treasure) {
        isTreasure = treasure;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
