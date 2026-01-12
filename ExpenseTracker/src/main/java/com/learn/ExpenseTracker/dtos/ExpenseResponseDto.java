package com.learn.ExpenseTracker.dtos;

import java.util.Date;

public class ExpenseResponseDto {
    private Long id;
    private String name;
    private Double amount;
//    private Date date;


    public ExpenseResponseDto(Long id, String name, Double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
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
}
