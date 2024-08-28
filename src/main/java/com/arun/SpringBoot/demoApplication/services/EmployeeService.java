package com.arun.SpringBoot.demoApplication.services;

import com.arun.SpringBoot.demoApplication.dto.EmployeeDTO;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(Long id);

    Boolean deleteEmployeeById(Long id);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO updateEmployeeById(Long id, EmployeeDTO employeeDTO);

    EmployeeDTO updatePartialEmployeeById(Long id, Map<String,Object> updates);

    Boolean isEmployeeExists(Long id);
}
