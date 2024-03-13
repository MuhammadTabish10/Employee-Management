package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto save(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployee(Boolean status);
    List<EmployeeDto> getAllEmployeesByJobTitle(Long jobTitleId);
    List<EmployeeDto> getAllEmployeesByDepartment(Long departmentId);
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto update(Long id, EmployeeDto employeeDto);
    void delete(Long id);
    void setToActive(Long id);
}
