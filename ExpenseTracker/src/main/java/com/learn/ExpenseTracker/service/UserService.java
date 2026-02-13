package com.learn.ExpenseTracker.service;

import com.learn.ExpenseTracker.dtos.*;
import com.learn.ExpenseTracker.model.AppUser;
import com.learn.ExpenseTracker.repository.UserRepository;
import com.learn.ExpenseTracker.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private UserDetailsService userDetailsService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    public ResponseEntity<?> addUser(RegisterRequest registerRequest) {
        try {
            if(userRepository.findByUsername(registerRequest.getUsername()) != null){
                throw new RuntimeException(registerRequest.getUsername() +"already exist");
            }
            if(registerRequest.getUsername() == null || registerRequest.getPassword() == null || registerRequest.getRole() == null){
                throw new RuntimeException("invalid credentials");
            }
            AppUser user = new AppUser();
            user.setUsername(registerRequest.getUsername());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setRole(registerRequest.getRole());
            userRepository.save(user);
            return new ResponseEntity<>(new RegisterResponse(user.getId(), user.getUsername(), user.getRole()), HttpStatus.OK);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> authenticateUser(LoginRequest request) {
        try{
            Authentication authRequest = new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                    );
            Authentication authentication = authenticationManager.authenticate(authRequest);

            SecurityContextHolder.getContext().setAuthentication(authentication);
//            UserDetails userDetail = userDetailsService.loadUserByUsername(request.getUsername());
            UserDetails userDetails  = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtUtils.generateToken(userDetails);
            return new ResponseEntity<>(new LoginResponse(jwtToken), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getUser() {
        try{
            org.springframework.security.core.userdetails.UserDetails userDetails  = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new ResponseEntity<>(new UserResponseDto(userDetails.getUsername()), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
