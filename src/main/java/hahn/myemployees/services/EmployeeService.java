package hahn.myemployees.services;

import hahn.myemployees.models.Employee;
import hahn.myemployees.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements iEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> showEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee searchEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }
}
