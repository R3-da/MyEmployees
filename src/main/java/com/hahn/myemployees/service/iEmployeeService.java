package com.hahn.myemployees.service;

import com.hahn.myemployees.model.Employee;

import java.util.List;

public interface iEmployeeService {
    public List<Employee> showEmployees();

    public Employee searchEmployeeById(int employeeId);

    public void saveEmployee(Employee employee);

    public void deleteEmployee(Employee employee);

}
