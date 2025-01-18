package hahn.myemployees.view;

import hahn.myemployees.models.Employee;
import hahn.myemployees.services.EmployeeService;
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
    private JTextField textEmployee;
    private JTextField textAuthor;
    private JTextField textValue;
    private JTextField textQuantity;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private DefaultTableModel tableModel;

    @Autowired
    public EmployeeForm(EmployeeService employeeService) {
        this.employeeService = employeeService;
        startForm();
        agregarButton.addActionListener(e -> addEmployee());
        modificarButton.addActionListener(e -> modifyEmployee());
        eliminarButton.addActionListener(e -> deleteEmployee());
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
        if (textEmployee.getText().equals("")) {
            showMessage("Proporciona el nombre del libro");
            textEmployee.requestFocusInWindow();
            return;
        }

        var employeeName = textEmployee.getText();
        var employeeAuthor = textAuthor.getText();
        var employeeValue = Double.parseDouble(textValue.getText());
        var employeeQuantity = Integer.parseInt(textQuantity.getText());

        // Create the book object
        var employee = new Employee(null, employeeName, employeeAuthor, employeeValue, employeeQuantity);
        this.employeeService.saveEmployee(employee);
        showMessage("The book has been added successfully");
        clearForm();
        showEmployees();

    }

    private void modifyEmployee() {
        if (this.textId.getText().equals("")) {
           showMessage("You must select a recordo");
        } else {

            // Check that the number of the book is zero
            if (textEmployee.getText().equals("")) {
                showMessage("Provide the name of the book");
                textEmployee.requestFocusInWindow();
                return;
            }

            // List the book object to update
            int employeeId = Integer.parseInt(textId.getText());
            var employeeTitle = textEmployee.getText();
            var employeeAuthor = textAuthor.getText();
            var employeeValue = Double.parseDouble(textValue.getText());
            var employeeQuantity = Integer.parseInt(textQuantity.getText());

            var employee = new Employee(employeeId, employeeTitle, employeeAuthor, employeeValue, employeeQuantity);
            employeeService.saveEmployee(employee);
            showMessage("The book has been successfully modified");
            clearForm();
            showEmployees();
        }
    }

    private void deleteEmployee() {
        var line = employeeTable.getSelectedRow();
        if (line != -1) {
            String employeeId = employeeTable.getModel().getValueAt(line, 0).toString();

            var employee = new Employee();
            employee.setEmployeeId(Integer.parseInt(employeeId));
            employeeService.deleteEmployee(employee);
            showMessage("The book has been deleted successfully");
            clearForm();
            showEmployees();
        } else {
            showMessage("You must select a record");
        }
    }

    private void clearForm() {
        textEmployee.setText("");
        textAuthor.setText("");
        textValue.setText("");
        textQuantity.setText("");
    }

    private void loadSelectedEmployee() {
        // Column indices start at 0
        var line = employeeTable.getSelectedRow();
        if (line != -1) {
            String employeeId = employeeTable.getModel().getValueAt(line, 0).toString();
            textId.setText(employeeId);
            String employeeTitle = employeeTable.getModel().getValueAt(line, 1).toString();
            textEmployee.setText(employeeTitle);
            String employeeAuthor = employeeTable.getModel().getValueAt(line, 2).toString();
            textAuthor.setText(employeeAuthor);
            String employeeValue = employeeTable.getModel().getValueAt(line, 3).toString();
            textValue.setText(employeeValue);
            String employeeQuantity = employeeTable.getModel().getValueAt(line, 4).toString();
            textQuantity.setText(employeeQuantity);
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
        String[] header = {"ID", "Name", "Author", "Price", "Quantity"};
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

        // Get all the books stored in the database
        var employees = employeeService.showEmployees();
        employees.forEach(employee -> {
            Object[] lineEmployee = {
                    employee.getEmployeeId(),
                    employee.getTitle(),
                    employee.getAuthor(),
                    employee.getPrice(),
                    employee.getQuantity()
            };
            this.tableModel.addRow(lineEmployee);
        });
    }
}
