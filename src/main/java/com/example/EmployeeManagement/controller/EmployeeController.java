package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        EmployeeDto employee = employeeService.save(employeeDto);
        return ResponseEntity.ok(employee);
    }
    @GetMapping("/employee/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<List<EmployeeDto>> getAllEmployees(@PathVariable Boolean status){
        List<EmployeeDto> employees = employeeService.getAllEmployee(status);
        return ResponseEntity.ok(employees);
    }
    @GetMapping("/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id){
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }
    @PutMapping("/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<EmployeeDto> updateEmployee(@Valid @PathVariable Long id, @RequestBody EmployeeDto employeeDto){
        EmployeeDto employee = employeeService.update(id,employeeDto);
        return ResponseEntity.ok(employee);
    }
    @DeleteMapping("/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<String> deleteEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.delete(id));
    }
}
