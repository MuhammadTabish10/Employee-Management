package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.SalaryDto;
import com.example.EmployeeManagement.service.SalaryService;
import javax.validation.Valid;
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
    public ResponseEntity<SalaryDto> createSalary(@Valid @RequestBody SalaryDto salaryDto){
        SalaryDto salary = salaryService.save(salaryDto);
        return ResponseEntity.ok(salary);
    }
    @GetMapping("/salary/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<SalaryDto>> getAllSalaries(@PathVariable Boolean status){
        List<SalaryDto> salaryDtoList = salaryService.getAllSalary(status);
        return ResponseEntity.ok(salaryDtoList);
    }
    @GetMapping("/salary/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SalaryDto> getSalaryById(@PathVariable Long id){
        SalaryDto salaryDto = salaryService.getSalaryById(id);
        return ResponseEntity.ok(salaryDto);
    }
    @PutMapping("/salary/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SalaryDto> updateSalary(@Valid @PathVariable Long id, @RequestBody SalaryDto salaryDto){
        SalaryDto salary = salaryService.update(id,salaryDto);
        return ResponseEntity.ok(salary);
    }
    @DeleteMapping("/salary/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteSalaryById(@PathVariable Long id){
        salaryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/salary/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> setSalaryStatusToActiveById(@PathVariable Long id) {
        salaryService.setToActive(id);
        return ResponseEntity.ok().build();
    }
}
