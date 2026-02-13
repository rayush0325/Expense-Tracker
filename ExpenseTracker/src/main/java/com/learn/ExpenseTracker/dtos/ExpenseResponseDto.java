package com.learn.ExpenseTracker.dtos;

import com.learn.ExpenseTracker.model.AppUser;

import java.util.Date;

public class ExpenseResponseDto {
    private Long id;
    private String name;
    private Double amount;
    private Long userId;
//    private Date date;


    public ExpenseResponseDto(Long id, String name, Double amount, Long userId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getUserId() {
        return userId;
    }
    //    public void setId(Long id) {
//        this.id = id;
//    }
    //    public AppUser getUser() {
//        return user;
//    }
}
