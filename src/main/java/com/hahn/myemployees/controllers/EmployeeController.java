package com.hahn.myemployees.controllers;

import com.hahn.myemployees.models.Employee;
import com.hahn.myemployees.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping
    public List<Employee> getAllEmployees() {
        logger.info("GET request received for all employees");
        List<Employee> employees = employeeService.showEmployees();
        logger.debug("Returning {} employees", employees.size());
        return employees;
    }
    
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        logger.info("GET request received for employee id: {}", id);
        return employeeService.searchEmployeeById(id);
    }
    
    @PostMapping
    public void createEmployee(@RequestBody Employee employee) {
        logger.info("POST request received to create new employee");
        employeeService.saveEmployee(employee);
        logger.debug("Employee created successfully");
    }
    
    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        logger.info("PUT request received to update employee id: {}", id);
        employee.setEmployeeId(id);
        employeeService.saveEmployee(employee);
        logger.debug("Employee updated successfully");
    }
    
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable int id) {
        logger.info("DELETE request received for employee id: {}", id);
        Employee employee = employeeService.searchEmployeeById(id);
        if (employee != null) {
            employeeService.deleteEmployee(employee);
            logger.debug("Employee deleted successfully");
        }
    }
}
