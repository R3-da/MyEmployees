package com.hahn.myemployees.controllers;

import com.hahn.myemployees.models.User;
import com.hahn.myemployees.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hahn.myemployees.dto.LoginRequest;

import java.util.logging.Logger;

@RestController
public class AuthController {
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserService userService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt for user: " + loginRequest.getUsername());
        User user = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            String token = userService.generateJwtToken(user);
            logger.info("Login successful for user: " + loginRequest.getUsername());
            return ResponseEntity.ok(token);
        }
        logger.warning("Login failed for user: " + loginRequest.getUsername());
        return ResponseEntity.badRequest().body("Invalid credentials");
    }
}



