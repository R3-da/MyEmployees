package com.hahn.myemployees.ui;

import javax.swing.*;
import java.awt.*;

import com.hahn.myemployees.service.EmployeeService;
import com.hahn.myemployees.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuScreen extends JPanel {
    private JButton employeesButton;
    private JButton departmentsButton;
    private JButton logoutButton;

    private final EmployeeService employeeService;
    private final UserService userService;

    @Autowired
    public MenuScreen(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
        initializePanel();
        createButtons();
        addButtonListeners();
    }

    private void initializePanel() {
        setLayout(new GridLayout(3, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void createButtons() {
        employeesButton = new JButton("Manage Employees");
        departmentsButton = new JButton("Manage Departments");
        logoutButton = new JButton("Logout");

        add(employeesButton);
        add(departmentsButton);
        add(logoutButton);
    }

    private void addButtonListeners() {
        employeesButton.addActionListener(e -> openEmployeeForm());
        departmentsButton.addActionListener(e -> openDepartmentForm());
        logoutButton.addActionListener(e -> logout());
    }

    private void openEmployeeForm() {
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
        EmployeeForm employeeForm = new EmployeeForm(employeeService, userService);
        employeeForm.setVisible(true);
    }

    private void openDepartmentForm() {
        // Implement department form opening logic
    }

    private void logout() {
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
        EmployeeForm employeeForm = new EmployeeForm(employeeService, userService);
        LoginScreen homeScreen = new LoginScreen(employeeForm, userService);
        homeScreen.setVisible(true);
    }

}
