package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto save(DepartmentDto departmentDto);
    List<DepartmentDto> getAllDepartment(Boolean status);
    DepartmentDto getDepartmentById(Long id);
    DepartmentDto update(Long id, DepartmentDto departmentDto);
    void delete(Long id);
    void setToActive(Long id);
}
