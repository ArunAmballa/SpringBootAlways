package com.arun.SpringBoot.demoApplication.controllers;

import com.arun.SpringBoot.demoApplication.advices.ApiResponse;
import com.arun.SpringBoot.demoApplication.dto.EmployeeDTO;
import com.arun.SpringBoot.demoApplication.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> allEmployees = employeeService.getAllEmployees();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }
    
    
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO){
        EmployeeDTO savedEmployee=employeeService.createNewEmployee(employeeDTO);
        return new ResponseEntity<>(savedEmployee,HttpStatus.CREATED);
        
    }
    
    
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id){
        EmployeeDTO employeeById = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeById,HttpStatus.OK);
    }

    
    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long id){
        EmployeeDTO updatedEmployee = employeeService.updateEmployeeById(id, employeeDTO);
        return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
    }

    @PatchMapping("/employees/update/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeePartially(@RequestBody Map<String, Object> updates, @PathVariable Long id){
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(id, updates);
        return new ResponseEntity<>(employeeDTO,HttpStatus.OK);
    }


    @DeleteMapping("/employee/{id}")
    public Boolean deleteEmployee(@PathVariable  Long id){
        return employeeService.deleteEmployeeById(id);
    }
}
