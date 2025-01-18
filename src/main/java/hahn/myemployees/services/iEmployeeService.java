package hahn.myemployees.services;

import hahn.myemployees.models.Employee;

import java.util.List;

public interface iEmployeeService {
    public List<Employee> showEmployees();

    public Employee searchEmployeeById(int employeeId);

    public void saveEmployee(Employee employee);

    public void deleteEmployee(Employee employee);

}
