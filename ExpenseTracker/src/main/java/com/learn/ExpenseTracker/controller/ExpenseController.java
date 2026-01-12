package com.learn.ExpenseTracker.controller;

import com.learn.ExpenseTracker.dtos.ExpenseRequestDto;
import com.learn.ExpenseTracker.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequestDto expenseRequestDto){
        return expenseService.addExpense(expenseRequestDto);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllExpense(){
        return expenseService.getAllExpense();
    }
}
