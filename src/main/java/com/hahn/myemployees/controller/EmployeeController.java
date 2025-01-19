package com.hahn.myemployees.controller;

import com.hahn.myemployees.model.Employee;
import com.hahn.myemployees.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "APIs for managing employee operations")
public class EmployeeController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    
    @Autowired
    private EmployeeService employeeService;
    
    @Operation(summary = "Get all employees", description = "Retrieves a list of all employees in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all employees")
    @GetMapping
    public List<Employee> getAllEmployees() {
        logger.info("GET request received for all employees");
        List<Employee> employees = employeeService.showEmployees();
        logger.debug("Returning {} employees", employees.size());
        return employees;
    }
    
    @Operation(summary = "Get employee by ID", description = "Retrieves a specific employee using their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved employee"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        logger.info("GET request received for employee id: {}", id);
        return employeeService.searchEmployeeById(id);
    }
    
    @Operation(summary = "Create new employee", description = "Creates a new employee record")
    @ApiResponse(responseCode = "201", description = "Employee successfully created")
    @PostMapping
    public void createEmployee(@RequestBody Employee employee) {
        logger.info("POST request received to create new employee");
        employeeService.saveEmployee(employee);
        logger.debug("Employee created successfully");
    }
    
    @Operation(summary = "Update employee", description = "Updates an existing employee's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employee successfully updated"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        logger.info("PUT request received to update employee id: {}", id);
        employeeService.saveEmployee(employee);
        logger.debug("Employee updated successfully");
    }
    
    @Operation(summary = "Delete employee", description = "Deletes an employee from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employee successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
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
