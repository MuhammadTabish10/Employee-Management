package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.SalaryDto;

import java.util.List;

public interface SalaryService {
    SalaryDto save(SalaryDto salaryDto);
    List<SalaryDto> getAllSalary(Boolean status);
    SalaryDto getSalaryById(Long id);
    SalaryDto update(Long id, SalaryDto salaryDto);
    String delete(Long id);
}
