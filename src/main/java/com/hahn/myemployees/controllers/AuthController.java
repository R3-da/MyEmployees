package com.hahn.myemployees.controllers;

import com.hahn.myemployees.models.User;
import com.hahn.myemployees.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hahn.myemployees.dto.LoginRequest;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            String token = userService.generateJwtToken(user);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }
}


