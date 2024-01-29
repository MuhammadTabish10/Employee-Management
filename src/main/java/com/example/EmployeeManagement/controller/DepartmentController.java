package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.DepartmentDto;
import com.example.EmployeeManagement.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/department")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody DepartmentDto departmentDto){
        DepartmentDto department = departmentService.save(departmentDto);
        return ResponseEntity.ok(department);
    }
    @GetMapping("/department/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<List<DepartmentDto>> getAllDepartments(@PathVariable Boolean status){
        List<DepartmentDto> departmentDtoList = departmentService.getAllDepartment(status);
        return ResponseEntity.ok(departmentDtoList);
    }
    @GetMapping("/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id){
        DepartmentDto departmentDto = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(departmentDto);
    }
    @PutMapping("/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<DepartmentDto> updateDepartment(@Valid @PathVariable Long id, @RequestBody DepartmentDto departmentDto){
        DepartmentDto department = departmentService.update(id,departmentDto);
        return ResponseEntity.ok(department);
    }
    @DeleteMapping("/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<String> deleteDepartmentById(@PathVariable Long id){
        return ResponseEntity.ok(departmentService.delete(id));
    }
}
