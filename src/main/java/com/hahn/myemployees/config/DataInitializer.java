package com.hahn.myemployees.config;

import com.hahn.myemployees.model.User;
import com.hahn.myemployees.model.UserRole;
import com.hahn.myemployees.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;
    
    @Override
    public void run(String... args) {
        if (userService.getAllUsers().isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123");
            adminUser.setRole(UserRole.ADMIN);
            
            userService.createUser(adminUser);
        }
    }
}
