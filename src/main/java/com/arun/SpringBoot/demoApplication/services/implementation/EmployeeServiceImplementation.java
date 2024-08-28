package com.arun.SpringBoot.demoApplication.services.implementation;

import com.arun.SpringBoot.demoApplication.dto.EmployeeDTO;
import com.arun.SpringBoot.demoApplication.entities.Employee;
import com.arun.SpringBoot.demoApplication.exceptions.ResourceAlreadyExistsException;
import com.arun.SpringBoot.demoApplication.exceptions.ResourceNotFoundException;
import com.arun.SpringBoot.demoApplication.repositories.EmployeeRepository;
import com.arun.SpringBoot.demoApplication.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final  ModelMapper modelMapper;

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        Employee inputEmployee=modelMapper.map(employeeDTO,Employee.class);
        Employee existedEmployee=employeeRepository.findByEmail(inputEmployee.getEmail());
        if(existedEmployee!=null){
            throw new ResourceAlreadyExistsException("Employee Already Exist With Given Email:"+inputEmployee.getEmail());
        }
        Employee savedEmployee=employeeRepository.save(inputEmployee);
        return modelMapper.map(savedEmployee,EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        isEmployeeExists(id);
        return modelMapper.map(employeeRepository.findById(id),EmployeeDTO.class);
    }

    @Override
    public Boolean deleteEmployeeById(Long id) {
        isEmployeeExists(id);
        employeeRepository.deleteById(id);
        return true;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> employees = employeeRepository.findAll()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
        return employees;
    }

    @Override
    public EmployeeDTO updateEmployeeById(Long id, EmployeeDTO employeeDTO) {
        isEmployeeExists(id);
        Employee inputEmployee=modelMapper.map(employeeDTO,Employee.class);
        Employee toBeUpdatedEmployee=employeeRepository.findById(id).get();
        toBeUpdatedEmployee.setEmail(inputEmployee.getEmail());
        toBeUpdatedEmployee.setName(inputEmployee.getName());
        toBeUpdatedEmployee.setSalary(inputEmployee.getSalary());
        toBeUpdatedEmployee.setAge(inputEmployee.getAge());
        Employee updatedEmployee=employeeRepository.save(toBeUpdatedEmployee);
        return modelMapper.map(updatedEmployee,EmployeeDTO.class);

    }

    @Override
    public EmployeeDTO updatePartialEmployeeById(Long id, Map<String, Object> updates) {
        isEmployeeExists(id);
        Employee employee=employeeRepository.findById(id).get();
        updates.forEach((key, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(Employee.class, key);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employee, value);
        });
      return modelMapper.map(employeeRepository.save(employee),EmployeeDTO.class);
    }

    @Override
    public Boolean isEmployeeExists(Long id){
        Boolean exist=employeeRepository.existsById(id);
        if(!exist) throw new ResourceNotFoundException("Employee Not Found With Given Id:"+id);
        return true;
    }

}
