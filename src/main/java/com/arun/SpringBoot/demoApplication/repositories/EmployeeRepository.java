package com.arun.SpringBoot.demoApplication.repositories;

import com.arun.SpringBoot.demoApplication.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);
}
