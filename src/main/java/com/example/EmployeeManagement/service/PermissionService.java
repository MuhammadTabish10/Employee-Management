package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.PermissionDto;

import java.util.List;

public interface PermissionService {
    List<PermissionDto> getAll();
    PermissionDto findById(Long id);
}
