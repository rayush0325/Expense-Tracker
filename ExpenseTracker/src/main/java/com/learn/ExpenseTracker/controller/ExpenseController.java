package com.learn.ExpenseTracker.controller;

import com.learn.ExpenseTracker.dtos.ExpenseRequestDto;
import com.learn.ExpenseTracker.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequestDto expenseRequestDto){
        return expenseService.addExpense(expenseRequestDto);
    }

    @GetMapping("/")

    public ResponseEntity<?> getAllExpense(){
        return expenseService.getAllExpense();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id){
        return expenseService.getById(id);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateExpenseById(@PathVariable Long id, @RequestBody ExpenseRequestDto updatedExpense){
        return expenseService.updateExpenseById(id, updatedExpense);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteExpenseById(@PathVariable Long id){
        return expenseService.deleteExpenseById(id);
    }
}
