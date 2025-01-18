package com.hahn.myemployees.repository;

import com.hahn.myemployees.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
