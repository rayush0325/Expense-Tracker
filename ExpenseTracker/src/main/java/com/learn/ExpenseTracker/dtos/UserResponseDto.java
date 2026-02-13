package com.learn.ExpenseTracker.dtos;

public class UserResponseDto {
    public UserResponseDto(String username) {
        this.username = username;
    }

    private String username;

    public String getUsername() {
        return username;
    }
}
