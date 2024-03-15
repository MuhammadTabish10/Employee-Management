package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.service.EmployeeService;
import javax.validation.Valid;

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
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        EmployeeDto employee = employeeService.save(employeeDto);
        return ResponseEntity.ok(employee);
    }
    @GetMapping("/employee/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(@PathVariable Boolean status){
        List<EmployeeDto> employees = employeeService.getAllEmployee(status);
        return ResponseEntity.ok(employees);
    }
    @GetMapping("/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id){
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }
    @PutMapping("/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employeeDto){
        EmployeeDto employee = employeeService.update(id,employeeDto);
        return ResponseEntity.ok(employee);
    }
    @DeleteMapping("/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id){
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/employee/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> setEmployeeStatusToActiveById(@PathVariable Long id) {
        employeeService.setToActive(id);
        return ResponseEntity.ok().build();
    }
}
