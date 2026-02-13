package com.learn.ExpenseTracker.dtos;

import com.learn.ExpenseTracker.model.AppUser;

public class ExpenseRequestDto {
    private String name;
    private Double amount;
//    private Long userId;
    public AppUser appUser;

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    //    public Long getUserId() {
//        return userId;
//    }
}
