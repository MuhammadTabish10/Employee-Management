package com.example.EmployeeManagement.service.impl;

import com.example.EmployeeManagement.dto.SalaryDto;
import com.example.EmployeeManagement.exception.RecordNotFoundException;
import com.example.EmployeeManagement.service.SalaryService;
import com.example.EmployeeManagement.model.Salary;
import com.example.EmployeeManagement.repository.EmployeeRepository;
import com.example.EmployeeManagement.repository.SalaryRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    public SalaryServiceImpl(SalaryRepository salaryRepository, EmployeeRepository employeeRepository) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public SalaryDto save(SalaryDto salaryDto) {
        Salary salary = toEntity(salaryDto);
        salary.setStatus(true);

        salary.setEmployee(employeeRepository.findById(salary.getEmployee().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Employee not found at id => %d", salary.getEmployee().getId()))));

        Salary createdSalary = salaryRepository.save(salary);
        return toDto(createdSalary);
    }

    @Override
    public List<SalaryDto> getAllSalary(Boolean status) {
        List<Salary> salaries = salaryRepository.findAllByStatus(status);
        List<SalaryDto> salaryDtoList = new ArrayList<>();

        for(Salary salary : salaries){
            SalaryDto salaryDto = toDto(salary);
            salaryDtoList.add(salaryDto);
        }
        return salaryDtoList;
    }

    @Override
    public SalaryDto getSalaryById(Long id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Salary Not found at id => %d", id)));
        return toDto(salary);
    }

    @Override
    @Transactional
    public SalaryDto update(Long id, SalaryDto salaryDto) {
        Salary existingSalary = salaryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Salary Not found at id => %d", id)));

        existingSalary.setAmount(salaryDto.getAmount());
        existingSalary.setStartDate(salaryDto.getStartDate());
        existingSalary.setEndDate(salaryDto.getEndDate());

        existingSalary.setEmployee(employeeRepository.findById(salaryDto.getEmployee().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Employee not found at id => %d", salaryDto.getEmployee().getId()))));

        Salary updatedSalary = salaryRepository.save(existingSalary);
        return toDto(updatedSalary);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Salary Not found at id => %d", id)));
        salaryRepository.setStatusWhereId(salary.getId(), false);
    }

    @Override
    @Transactional
    public void setToActive(Long id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Salary Not found at id => %d", id)));
        salaryRepository.setStatusWhereId(salary.getId(), true);
    }

    public SalaryDto toDto(Salary salary){
        return SalaryDto.builder()
                .id(salary.getId())
                .createdAt(salary.getCreatedAt())
                .amount(salary.getAmount())
                .startDate(salary.getStartDate())
                .endDate(salary.getEndDate())
                .employee(salary.getEmployee())
                .status(salary.getStatus())
                .build();
    }

    public Salary toEntity(SalaryDto salaryDto){
        return Salary.builder()
                .id(salaryDto.getId())
                .createdAt(salaryDto.getCreatedAt())
                .amount(salaryDto.getAmount())
                .startDate(salaryDto.getStartDate())
                .endDate(salaryDto.getEndDate())
                .employee(salaryDto.getEmployee())
                .status(salaryDto.getStatus())
                .build();
    }
}
