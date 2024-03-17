package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto addRole(RoleDto roleDto);
    List<RoleDto> getAll();
    RoleDto findById(Long id);
    RoleDto updateRole(Long id, RoleDto roleDto);
}
