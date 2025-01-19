package com.hahn.myemployees.service;

import com.hahn.myemployees.model.Employee;
import com.hahn.myemployees.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showEmployees_ReturnsAllEmployees() {
        // Arrange
        Employee emp1 = new Employee();
        emp1.setId(1);
        // emp1.setName("John Doe");
        
        Employee emp2 = new Employee();
        emp2.setId(2);
        // emp2.setName("Jane Doe");
        
        List<Employee> expectedEmployees = Arrays.asList(emp1, emp2);
        when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        // Act
        List<Employee> actualEmployees = employeeService.showEmployees();

        // Assert
        assertEquals(expectedEmployees, actualEmployees);
        verify(employeeRepository).findAll();
    }

    @Test
    void showEmployees_ReturnsEmptyList_WhenNoEmployees() {
        // Arrange
        when(employeeRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Employee> actualEmployees = employeeService.showEmployees();

        // Assert
        assertTrue(actualEmployees.isEmpty());
        verify(employeeRepository).findAll();
    }

    @Test
    void searchEmployeeById_ReturnsEmployee_WhenFound() {
        // Arrange
        Employee expected = new Employee();
        expected.setId(1);
        // expected.setName("John Doe");
        
        when(employeeRepository.findById(1)).thenReturn(Optional.of(expected));

        // Act
        Employee actual = employeeService.searchEmployeeById(1);

        // Assert
        assertEquals(expected, actual);
        verify(employeeRepository).findById(1);
    }

    @Test
    void searchEmployeeById_ReturnsNull_WhenNotFound() {
        // Arrange
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Employee actual = employeeService.searchEmployeeById(999);

        // Assert
        assertNull(actual);
        verify(employeeRepository).findById(999);
    }

    @Test
    void saveEmployee_SavesSuccessfully() {
        // Arrange
        Employee employee = new Employee();
        // employee.setName("John Doe");

        // Act
        employeeService.saveEmployee(employee);

        // Assert
        verify(employeeRepository).save(employee);
    }

    @Test
    void deleteEmployee_DeletesSuccessfully() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1);
        // employee.setName("John Doe");

        // Act
        employeeService.deleteEmployee(employee);

        // Assert
        verify(employeeRepository).delete(employee);
    }
}
