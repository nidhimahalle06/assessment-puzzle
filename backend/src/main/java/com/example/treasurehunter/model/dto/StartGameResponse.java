package com.example.treasurehunter.model.dto;

public class StartGameResponse {
    private String userId;

    public StartGameResponse() {}

    public StartGameResponse(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
