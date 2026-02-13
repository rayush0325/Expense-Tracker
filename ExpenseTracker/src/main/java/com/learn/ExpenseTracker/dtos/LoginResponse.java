package com.learn.ExpenseTracker.dtos;

public class LoginResponse {
    private String jwtToken;

    public LoginResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
