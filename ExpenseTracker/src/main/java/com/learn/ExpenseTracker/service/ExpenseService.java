package com.learn.ExpenseTracker.service;

import com.learn.ExpenseTracker.dtos.ExpenseRequestDto;
import com.learn.ExpenseTracker.dtos.ExpenseResponseDto;
import com.learn.ExpenseTracker.model.Expense;
import com.learn.ExpenseTracker.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public ResponseEntity<?> addExpense(ExpenseRequestDto expenseRequestDto) {
        try {
            if (expenseRequestDto.getName() == null || expenseRequestDto.getName().length() == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid expense name");
            }
            if (expenseRequestDto.getAmount() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid expense amount");
            }
            Expense expense = new Expense();
            expense.setName(expenseRequestDto.getName());
            expense.setAmount(expenseRequestDto.getAmount());
            expenseRepository.save(expense);
            return new ResponseEntity<>(new ExpenseResponseDto(
                    expense.getId(),
                    expense.getName(),
                    expense.getAmount()
            ), HttpStatus.OK);

        }catch (RuntimeException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllExpense() {
        List<Expense> expenseList = expenseRepository.findAll();
        if(expenseList.isEmpty()){
            return new ResponseEntity<>("no expenses yet", HttpStatus.OK);
        }
        return new ResponseEntity<>(expenseList, HttpStatus.OK);
    }
}
