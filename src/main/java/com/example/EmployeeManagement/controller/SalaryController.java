package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.SalaryDto;
import com.example.EmployeeManagement.service.SalaryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SalaryController {
    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping("/salary")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<SalaryDto> createSalary(@Valid @RequestBody SalaryDto salaryDto){
        SalaryDto salary = salaryService.save(salaryDto);
        return ResponseEntity.ok(salary);
    }
    @GetMapping("/salary/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<List<SalaryDto>> getAllSalaries(@PathVariable Boolean status){
        List<SalaryDto> salaryDtoList = salaryService.getAllSalary(status);
        return ResponseEntity.ok(salaryDtoList);
    }
    @GetMapping("/salary/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<SalaryDto> getSalaryById(@PathVariable Long id){
        SalaryDto salaryDto = salaryService.getSalaryById(id);
        return ResponseEntity.ok(salaryDto);
    }
    @PutMapping("/salary/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<SalaryDto> updateSalary(@Valid @PathVariable Long id, @RequestBody SalaryDto salaryDto){
        SalaryDto salary = salaryService.update(id,salaryDto);
        return ResponseEntity.ok(salary);
    }
    @DeleteMapping("/salary/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<String> deleteSalaryById(@PathVariable Long id){
        return ResponseEntity.ok(salaryService.delete(id));
    }
}
