package com.hahn.myemployees.view;

import com.hahn.myemployees.model.Employee;
import com.hahn.myemployees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class EmployeeForm extends JFrame {

    EmployeeService employeeService;
    private JPanel panel;
    private JTable employeeTable;
    private JTextField textId;
    private JTextField textFullName;
    private JTextField textJobTitle;
    private JTextField textDepartmentId;
    private JTextField textHireDate;
    private JTextField textEmploymentStatus;
    private JTextField textContactInfo;
    private JTextField textAddress;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;

    private DefaultTableModel tableModel;

    @Autowired
    public EmployeeForm(EmployeeService employeeService) {
        this.employeeService = employeeService;
        startForm();
        addButton.addActionListener(e -> addEmployee());
        modifyButton.addActionListener(e -> modifyEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        employeeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadSelectedEmployee();
            }
        });
    }

    private void startForm() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }

    private void addEmployee() {
        // Read the form values
        if (textFullName.getText().equals("")) {
            showMessage("Provide the name of the employee");
            textFullName.requestFocusInWindow();
            return;
        }

        var employeeFullName = textFullName.getText();
        var employeeJobTitle = textJobTitle.getText();
        var employeeDepartmentId = Integer.parseInt(textDepartmentId.getText());
        var employeeHireDate = textHireDate.getText();
        var employeeEmploymentStatus = textEmploymentStatus.getText();
        var employeeContactInfo = textContactInfo.getText();
        var employeeAddress = textAddress.getText();

        // Create the employee object
        var employee = new Employee(
                null,
                employeeFullName,
                employeeJobTitle,
                employeeDepartmentId,
                employeeHireDate,
                employeeEmploymentStatus,
                employeeContactInfo,
                employeeAddress
        );
        this.employeeService.saveEmployee(employee);
        showMessage("The employee has been added successfully");
        clearForm();
        showEmployees();

    }

    private void modifyEmployee() {
        if (this.textId.getText().equals("")) {
           showMessage("You must select a record");
        } else {

            // Check that the number of the employee is zero
            if (textFullName.getText().equals("")) {
                showMessage("Provide the name of the employee");
                textFullName.requestFocusInWindow();
                return;
            }

            var employeeFullName = textFullName.getText();
            var employeeJobTitle = textJobTitle.getText();
            var employeeDepartmentId = Integer.parseInt(textDepartmentId.getText());
            var employeeHireDate = textHireDate.getText();
            var employeeEmploymentStatus = textEmploymentStatus.getText();
            var employeeContactInfo = textContactInfo.getText();
            var employeeAddress = textAddress.getText();

            // Create the employee object
            var employee = new Employee(
                    null,
                    employeeFullName,
                    employeeJobTitle,
                    employeeDepartmentId,
                    employeeHireDate,
                    employeeEmploymentStatus,
                    employeeContactInfo,
                    employeeAddress
            );
            employeeService.saveEmployee(employee);
            showMessage("The employee has been successfully modified");
            clearForm();
            showEmployees();
        }
    }

    private void deleteEmployee() {
        var line = employeeTable.getSelectedRow();
        if (line != -1) {
            String employeeId = employeeTable.getModel().getValueAt(line, 0).toString();

            var employee = new Employee();
            employeeService.deleteEmployee(employee);
            showMessage("The employee has been deleted successfully");
            clearForm();
            showEmployees();
        } else {
            showMessage("You must select a record");
        }
    }

    private void clearForm() {
        textFullName.setText("");
        textJobTitle.setText("");
        textDepartmentId.setText("");
        textHireDate.setText("");
        textEmploymentStatus.setText("");
        textContactInfo.setText("");
        textAddress.setText("");
    }

    private void loadSelectedEmployee() {
        // Column indices start at 0
        var line = employeeTable.getSelectedRow();
        if (line != -1) {
            String employeeId = employeeTable.getModel().getValueAt(line, 0).toString();
            textId.setText(employeeId);
            String employeeFullName = employeeTable.getModel().getValueAt(line, 1).toString();
            textFullName.setText(employeeFullName);
            String employeeJobTitle = employeeTable.getModel().getValueAt(line, 2).toString();
            textJobTitle.setText(employeeJobTitle);
            String employeeDepartmentId = employeeTable.getModel().getValueAt(line, 3).toString();
            textDepartmentId.setText(employeeDepartmentId);
            String employeeHireDate = employeeTable.getModel().getValueAt(line, 4).toString();
            textHireDate.setText(employeeHireDate);
            String employeeEmploymentStatus = employeeTable.getModel().getValueAt(line, 5).toString();
            textEmploymentStatus.setText(employeeEmploymentStatus);
            String employeeContactInfo = employeeTable.getModel().getValueAt(line, 6).toString();
            textContactInfo.setText(employeeContactInfo);
            String employeeAddress = employeeTable.getModel().getValueAt(line, 7).toString();
            textAddress.setText(employeeAddress);
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void createUIComponents() {
        // We create the hidden textId element
        textId = new JTextField("");
        textId.setVisible(false);

        // TODO: place custom component creation code here
        this.tableModel = new DefaultTableModel(0, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] header = {"ID", "Full Name", "Job Title", "Department ID", "Hire Date", "Employment Status", "Contact Info", "Address"};
        this.tableModel.setColumnIdentifiers(header);

        // Instantiate the JTable object
        this.employeeTable = new JTable(tableModel);

        // Avoid selecting various records from the table
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        showEmployees();
    }

    private void showEmployees() {

        // Clean the table
        tableModel.setRowCount(0);

        // Get all the employees stored in the database
        var employees = employeeService.showEmployees();
        employees.forEach(employee -> {
            Object[] lineEmployee = {
                    employee.getId(),
                    employee.getFullName(),
                    employee.getJobTitle(),
                    employee.getDepartmentId(),
                    employee.getHireDate(),
                    employee.getEmploymentStatus(),
                    employee.getContactInfo(),
                    employee.getAddress()
            };
            this.tableModel.addRow(lineEmployee);
        });
    }
}
