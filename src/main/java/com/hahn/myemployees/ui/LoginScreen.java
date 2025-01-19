package com.hahn.myemployees.ui;

import com.hahn.myemployees.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;
import com.hahn.myemployees.model.User;
import com.hahn.myemployees.service.UserService;

import com.hahn.myemployees.service.EmployeeService;

@Component
public class LoginScreen extends JFrame {
    
    @Autowired
    private UserService userService;
    private EmployeeService employeeService;

    private JPanel loginPanel;
    private JPanel menuScreen;
    private JTextField usernameField;
    private JPasswordField passwordField;

    @Autowired
    public LoginScreen(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
        setTitle("MyEmployees System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Login Panel
        loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel("")); // Empty for layout
        loginPanel.add(loginButton);

        // Menu Panel
        menuScreen = new JPanel(new GridLayout(3, 1, 10, 10));
        menuScreen.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        loginButton.addActionListener(e -> {
            User user = userService.authenticate(
                usernameField.getText(),
                new String(passwordField.getPassword())
            );
            if (user != null) {
                showMenuForRole(user.getRole());
            } else {
                JOptionPane.showMessageDialog(this,
                    "Invalid credentials",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        add(loginPanel);
    }

    private void showMenuForRole(UserRole role) {
        dispose(); // Close the login window
        MenuScreen menuScreen = new MenuScreen(employeeService, userService);
        JFrame menuFrame = new JFrame("MyEmployees System");
        menuFrame.setSize(600, 400);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.add(menuScreen);
        menuFrame.setVisible(true);
    }

}
