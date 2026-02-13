package com.learn.ExpenseTracker.service;

import com.learn.ExpenseTracker.dtos.ExpenseRequestDto;
import com.learn.ExpenseTracker.dtos.ExpenseResponseDto;
import com.learn.ExpenseTracker.model.AppUser;
import com.learn.ExpenseTracker.model.Expense;
import com.learn.ExpenseTracker.repository.ExpenseRepository;
import com.learn.ExpenseTracker.repository.UserRepository;
import io.jsonwebtoken.lang.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;
    private UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> addExpense(ExpenseRequestDto expenseRequestDto) {
        try {
            if (expenseRequestDto.getName() == null || expenseRequestDto.getName().length() == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid expense name");
            }
            if (expenseRequestDto.getAmount() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid expense amount");
            }
//            if(expenseRequestDto.getUserId() == null){
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user id is null");
//            }



            Expense expense = new Expense();
            expense.setName(expenseRequestDto.getName());
            expense.setAmount(expenseRequestDto.getAmount());
            org.springframework.security.core.userdetails.UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            AppUser user = userRepository.findByUsername(userDetails.getUsername());
            expense.setUser(user);
            expenseRepository.save(expense);
            return new ResponseEntity<>(new ExpenseResponseDto(
                    expense.getId(),
                    expense.getName(),
                    expense.getAmount(),
                    expense.getUser().getId()
            ), HttpStatus.OK);

        }catch (RuntimeException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllExpense() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser user = userRepository.findByUsername(userDetails.getUsername());
//        List<Expense> expenseList = expenseRepository.findAll()
//                .stream().filter(expense -> Objects.equals(expense.getUser().getId(), user.getId()))
//                .collect(Collectors.toUnmodifiableList());
        List<ExpenseResponseDto> expenseList = expenseRepository.findAll()
                .stream()
                .filter(expense -> expense.getUser().getId() == user.getId())
                .map(expense -> {
                            return new ExpenseResponseDto(
                                    expense.getId(),
                                    expense.getName(),
                                    expense.getAmount(),
                                    expense.getUser().getId()
                            );
                        }
                ).toList();
        if(expenseList.isEmpty()){
            return new ResponseEntity<>("no expenses yet", HttpStatus.OK);
        }
        return new ResponseEntity<>(expenseList, HttpStatus.OK);
    }

    public ResponseEntity<?> getById(Long id) {
        try {
            Expense expense = expenseRepository.findById(id).orElse(null);
            if(expense == null){
                throw new RuntimeException("expense not found");
            }
            return new ResponseEntity<>(new ExpenseResponseDto(id, expense.getName(), expense.getAmount(), expense.getUser().getId()), HttpStatus.OK);

        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateExpenseById(Long id, ExpenseRequestDto updatedExpense) {
        try {
            Expense expense = expenseRepository.findById(id).orElse(null);
            if(expense == null){
                throw new RuntimeException("expense not found");
            }
            if(updatedExpense.getAmount() <= 0){
                throw new RuntimeException("invalid amount");
            }
            if(updatedExpense.getName().length() == 0){
                throw new RuntimeException("invalid name");
            }
            expense.setName(updatedExpense.getName());
            expense.setAmount(updatedExpense.getAmount());
            expenseRepository.save(expense);
            return new ResponseEntity<>(new ExpenseResponseDto(expense.getId(), expense.getName(), expense.getAmount(), expense.getUser().getId()), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<?> deleteExpenseById(Long id) {
        try {
            Expense expense = expenseRepository.findById(id).orElse(null);
            if(expense == null){
                throw new RuntimeException("expense not found");
            }
            expenseRepository.deleteById(id);
            return new ResponseEntity<>(new ExpenseResponseDto(expense.getId(), expense.getName(), expense.getAmount(), expense.getUser().getId()), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
