package com.example.EmployeeManagement.service.impl;

import com.example.EmployeeManagement.dto.DepartmentDto;
import com.example.EmployeeManagement.exception.RecordNotFoundException;
import com.example.EmployeeManagement.service.DepartmentService;
import com.example.EmployeeManagement.model.Department;
import com.example.EmployeeManagement.repository.DepartmentRepository;
import com.example.EmployeeManagement.repository.EmployeeRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public DepartmentDto save(DepartmentDto departmentDto) {
        Department department = toEntity(departmentDto);
        department.setStatus(true);
        Department createdDepartment = departmentRepository.save(department);
        return toDto(createdDepartment);
    }

    @Override
    public List<DepartmentDto> getAllDepartment(Boolean status) {
        List<Department> departmentList = departmentRepository.findAllByStatusOrderByIdDesc(status);
        List<DepartmentDto> departmentDtoList = new ArrayList<>();

        for(Department department : departmentList){
            DepartmentDto departmentDto = toDto(department);
            departmentDtoList.add(departmentDto);
        }
        return departmentDtoList;
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Department Not found at id => %d", id)));
        return toDto(department);
    }

    @Override
    @Transactional
    public DepartmentDto update(Long id, DepartmentDto departmentDto) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Department Not found at id => %d", id)));

        existingDepartment.setName(departmentDto.getName());

        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return toDto(updatedDepartment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Department Not found at id => %d", id)));
        employeeRepository.setStatusWhereId(department.getId(), false);
    }

    @Override
    @Transactional
    public void setToActive(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Department Not found at id => %d", id)));
        employeeRepository.setStatusWhereId(department.getId(), true);
    }

    public DepartmentDto toDto(Department department){
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .status(department.getStatus())
                .build();
    }

    public Department toEntity(DepartmentDto departmentDto){
        return Department.builder()
                .id(departmentDto.getId())
                .name(departmentDto.getName())
                .status(departmentDto.getStatus())
                .build();
    }
}
