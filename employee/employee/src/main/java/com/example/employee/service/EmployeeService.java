package com.example.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Transactional
    public Employee saveEmployee(Employee employee) {
        // For new employee, ensure ID is null
        if (employee.getId() == null) {
            return employeeRepository.save(employee);
        }
        
        // For updates, check if employee exists
        Optional<Employee> existingEmployee = employeeRepository.findById(employee.getId());
        if (existingEmployee.isEmpty()) {
            throw new RuntimeException("Employee not found with id: " + employee.getId());
        }
        return employeeRepository.save(employee);
    }
    
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }
} 