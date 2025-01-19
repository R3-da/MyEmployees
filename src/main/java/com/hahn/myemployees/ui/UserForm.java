package com.hahn.myemployees.ui;

import com.hahn.myemployees.model.User;
import com.hahn.myemployees.model.UserRole;
import com.hahn.myemployees.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserForm extends JFrame {
    private UserService userService;
    private JTable userTable;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<UserRole> roleComboBox;
    private User selectedUser;

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
        JButton clearButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // Create table
        String[] columns = {"Username", "Role"};
        userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Add components
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Table selection listener
        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && userTable.getSelectedRow() != -1) {
                int selectedRow = userTable.getSelectedRow();
                selectedUser = userService.getAllUsers().get(selectedRow);
                populateFields(selectedUser);
            }
        });

        // Button listeners
        addButton.addActionListener(e -> {
            if (validateInput()) {
                User newUser = new User();
                newUser.setUsername(usernameField.getText());
                newUser.setPassword(new String(passwordField.getPassword()));
                newUser.setRole((UserRole) roleComboBox.getSelectedItem());
                userService.createUser(newUser);
                refreshTable();
                clearForm();
            }
        });

        updateButton.addActionListener(e -> {
            if (selectedUser != null && validateInput()) {
                selectedUser.setUsername(usernameField.getText());
                if (passwordField.getPassword().length > 0) {
                    selectedUser.setPassword(new String(passwordField.getPassword()));
                }
                selectedUser.setRole((UserRole) roleComboBox.getSelectedItem());
                userService.updateUser(selectedUser);
                refreshTable();
                clearForm();
            }
        });

        deleteButton.addActionListener(e -> {
            if (selectedUser != null) {
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this user?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    userService.deleteUser(selectedUser.getId());
                    refreshTable();
                    clearForm();
                }
            }
        });

        clearButton.addActionListener(e -> clearForm());

        refreshTable();
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

    private void populateFields(User user) {
        usernameField.setText(user.getUsername());
        passwordField.setText("");  // Don't populate password for security
        roleComboBox.setSelectedItem(user.getRole());
    }

    private void clearForm() {
        usernameField.setText("");
        passwordField.setText("");
        roleComboBox.setSelectedIndex(0);
        selectedUser = null;
        userTable.clearSelection();
    }

    private boolean validateInput() {
        if (usernameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username is required");
            return false;
        }
        if (selectedUser == null && passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Password is required for new users");
            return false;
        }
        return true;
    }
}
