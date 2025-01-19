package com.hahn.myemployees.ui;

import com.hahn.myemployees.model.User;
import com.hahn.myemployees.model.UserRole;
import com.hahn.myemployees.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import java.util.List;



public class UserForm extends JFrame {
    private UserService userService;
    private JTable userTable;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<UserRole> roleComboBox;

    public UserForm(UserService userService) {
        this.userService = userService;
        setTitle("User Management");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<>(UserRole.values());

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleComboBox);

        // Create buttons
        JButton addButton = new JButton("Add User");
        JButton updateButton = new JButton("Update User");
        JButton deleteButton = new JButton("Delete User");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Create table
        String[] columns = {"Username", "Role"};
        userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Add components
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        refreshTable();

        // Add button listeners
        addButton.addActionListener(e -> {
            User newUser = new User();
            newUser.setUsername(usernameField.getText());
            newUser.setPassword(new String(passwordField.getPassword()));
            newUser.setRole((UserRole) roleComboBox.getSelectedItem());
            userService.createUser(newUser);
            refreshTable();
            clearForm();
        });

        setLocationRelativeTo(null);
    }

    private void refreshTable() {
        List<User> users = userService.getAllUsers();
        String[] columns = {"Username", "Role"};
        Object[][] data = new Object[users.size()][2];
        
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            data[i][0] = user.getUsername();
            data[i][1] = user.getRole();
        }
        
        userTable.setModel(new DefaultTableModel(data, columns));
    }

    private void clearForm() {
        usernameField.setText("");
        passwordField.setText("");
        roleComboBox.setSelectedIndex(0);
    }
}
