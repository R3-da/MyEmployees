package hahn.myemployees.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class HomeScreen extends JFrame {

    private JButton navigateButton;

    @Autowired
    public HomeScreen(EmployeeForm employeeForm) {
        setTitle("Home Screen");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        navigateButton = new JButton("Go to Employee Form");
        navigateButton.addActionListener(e -> {
            employeeForm.setVisible(true);
            this.setVisible(false);
        });

        setLayout(new BorderLayout());
        add(navigateButton, BorderLayout.CENTER);
    }
}