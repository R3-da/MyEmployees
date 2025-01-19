package com.hahn.myemployees.ui;

import com.hahn.myemployees.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;
import com.hahn.myemployees.model.User;
import com.hahn.myemployees.service.UserService;

@Component
public class LoginScreen extends JFrame {
    
    @Autowired
    private UserService userService;

    private JPanel loginPanel;
    private JPanel menuScreen;
    private JTextField usernameField;
    private JPasswordField passwordField;

    @Autowired
    public LoginScreen(EmployeeForm employeeForm, UserService userService) {
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
                showMenuForRole(user.getRole(), employeeForm);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Invalid credentials",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        add(loginPanel);
    }

    private void showMenuForRole(UserRole role, EmployeeForm employeeForm) {
        loginPanel.setVisible(false);
        menuScreen.removeAll();

        switch (role) {
            case ADMIN:
                addAdminMenuItems(employeeForm);
                break;
            case HR:
                addHRMenuItems(employeeForm);
                break;
            case MANAGER:
                addManagerMenuItems(employeeForm);
                break;
        }

        add(menuScreen);
        menuScreen.setVisible(true);
        revalidate();
        repaint();
    }

    private void addAdminMenuItems(EmployeeForm employeeForm) {
        JButton manageEmployees = new JButton("Manage Employees");
        JButton manageUsers = new JButton("Manage Users");
        JButton logoutButton = new JButton("Logout");

        menuScreen.add(manageEmployees);
        menuScreen.add(manageUsers);
        menuScreen.add(logoutButton);

        manageEmployees.addActionListener(e -> {
            employeeForm.setVisible(true);
            this.setVisible(false);
        });

        manageUsers.addActionListener(e -> {
            User currentUser = userService.authenticate(usernameField.getText(), new String(passwordField.getPassword()));
            if (currentUser != null && userService.hasPermission(currentUser, "MANAGE_USERS")) {
                UserForm userForm = new UserForm(userService);
                userForm.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Access denied. Admin privileges required.");
            }
        });

        logoutButton.addActionListener(e -> logout());
    }

    private void addHRMenuItems(EmployeeForm employeeForm) {
        JButton manageEmployees = new JButton("Manage Employees");
        JButton viewReports = new JButton("View Reports");
        JButton logoutButton = new JButton("Logout");

        menuScreen.add(manageEmployees);
        menuScreen.add(viewReports);
        menuScreen.add(logoutButton);

        manageEmployees.addActionListener(e -> {
            employeeForm.setVisible(true);
            this.setVisible(false);
        });

        logoutButton.addActionListener(e -> logout());
    }

    private void addManagerMenuItems(EmployeeForm employeeForm) {
        JButton viewTeam = new JButton("View Team Members");
        JButton manageTeam = new JButton("Manage Team");
        JButton logoutButton = new JButton("Logout");

        menuScreen.add(viewTeam);
        menuScreen.add(manageTeam);
        menuScreen.add(logoutButton);

        viewTeam.addActionListener(e -> {
            employeeForm.setVisible(true);
            this.setVisible(false);
        });

        logoutButton.addActionListener(e -> logout());
    }

    private void logout() {
        usernameField.setText("");
        passwordField.setText("");
        menuScreen.setVisible(false);
        loginPanel.setVisible(true);
        revalidate();
        repaint();
    }
}
