package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.DepartmentDto;
import com.example.EmployeeManagement.service.DepartmentService;
import javax.validation.Valid;
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
    public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody DepartmentDto departmentDto){
        DepartmentDto department = departmentService.save(departmentDto);
        return ResponseEntity.ok(department);
    }
    @GetMapping("/department/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(@PathVariable Boolean status){
        List<DepartmentDto> departmentDtoList = departmentService.getAllDepartment(status);
        return ResponseEntity.ok(departmentDtoList);
    }
    @GetMapping("/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id){
        DepartmentDto departmentDto = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(departmentDto);
    }
    @PutMapping("/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentDto departmentDto){
        DepartmentDto department = departmentService.update(id,departmentDto);
        return ResponseEntity.ok(department);
    }
    @DeleteMapping("/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteDepartmentById(@PathVariable Long id){
        departmentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/department/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> setDepartmentStatusToActiveById(@PathVariable Long id) {
        departmentService.setToActive(id);
        return ResponseEntity.ok().build();
    }
}
