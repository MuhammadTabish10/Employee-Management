package com.example.EmployeeManagement.service.impl;

import com.example.EmployeeManagement.dto.PermissionDto;
import com.example.EmployeeManagement.exception.RecordNotFoundException;
import com.example.EmployeeManagement.model.Permission;
import com.example.EmployeeManagement.repository.PermissionRepository;
import com.example.EmployeeManagement.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<PermissionDto> getAll() {
        List<Permission> permissionList = permissionRepository.findAll();
        List<PermissionDto> permissionDtoList = new ArrayList<>();

        for (Permission permission : permissionList) {
            PermissionDto permissionDto = toDto(permission);
            permissionDtoList.add(permissionDto);
        }
        return permissionDtoList;
    }

    @Override
    public PermissionDto findById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Permission Not found at id => %d", id)));
        return toDto(permission);
    }
    public PermissionDto toDto(Permission permission) {
        return PermissionDto.builder()
                .id(permission.getId())
                .name(permission.getName())
                .status(permission.getStatus())
                .build();
    }

    public Permission toEntity(PermissionDto permissionDto) {
        return Permission.builder()
                .id(permissionDto.getId())
                .name(permissionDto.getName())
                .status(permissionDto.getStatus())
                .build();
    }

}
