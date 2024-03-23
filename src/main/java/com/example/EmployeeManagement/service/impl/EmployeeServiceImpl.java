package com.example.EmployeeManagement.service.impl;

import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.exception.RecordNotFoundException;
import com.example.EmployeeManagement.model.Attendance;
import com.example.EmployeeManagement.model.Salary;
import com.example.EmployeeManagement.repository.*;
import com.example.EmployeeManagement.service.EmployeeService;
import com.example.EmployeeManagement.model.Employee;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.EmployeeManagement.util.Helper.validateEmployeeDto;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final SalaryRepository salaryRepository;
    private final JobTitleRepository jobTitleRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, AttendanceRepository attendanceRepository, SalaryRepository salaryRepository, JobTitleRepository jobTitleRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.salaryRepository = salaryRepository;
        this.jobTitleRepository = jobTitleRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public EmployeeDto save(EmployeeDto employeeDto) {
        validateEmployeeDto(employeeDto);
        Employee employee = toEntity(employeeDto);
        employee.setStatus(true);

        employee.setJobTitle(jobTitleRepository.findById(employee.getJobTitle().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("JobTitle not found at id => %d", employee.getJobTitle().getId()))));

        employee.setDepartment(departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Department not found at id => %d", employee.getDepartment().getId()))));

        return toDto(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeDto> getAllEmployee(Boolean status) {
        List<Employee> employeeList = employeeRepository.findAllByStatusOrderByIdDesc(status);
        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        for(Employee employee : employeeList){
            EmployeeDto employeeDto = toDto(employee);
            employeeDtoList.add(employeeDto);
        }
        return employeeDtoList;
    }

    @Override
    public List<EmployeeDto> getAllEmployeesByJobTitle(Long jobTitleId) {
        List<Employee> employeeList = employeeRepository.findAllByJobTitle_Id(jobTitleId);
        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        for(Employee employee : employeeList){
            EmployeeDto employeeDto = toDto(employee);
            employeeDtoList.add(employeeDto);
        }
        return employeeDtoList;
    }

    @Override
    public List<EmployeeDto> getAllEmployeesByDepartment(Long departmentId) {
        List<Employee> employeeList = employeeRepository.findAllByDepartment_Id(departmentId);
        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        for(Employee employee : employeeList){
            EmployeeDto employeeDto = toDto(employee);
            employeeDtoList.add(employeeDto);
        }
        return employeeDtoList;
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Employee Not found at id => %d", id)));
        return toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeDto update(Long id, EmployeeDto employeeDto) {
        validateEmployeeDto(employeeDto);

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Employee Not found at id => %d", id)));

        existingEmployee.setFirstName(employeeDto.getFirstName());
        existingEmployee.setLastName(employeeDto.getLastName());
        existingEmployee.setAddress(employeeDto.getAddress());
        existingEmployee.setHireDate(employeeDto.getHireDate());
        existingEmployee.setDateOfBirth(employeeDto.getDateOfBirth());
        existingEmployee.setPhoneNumber(employeeDto.getPhoneNumber());

        existingEmployee.setJobTitle(jobTitleRepository.findById(employeeDto.getJobTitle().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("JobTitle not found at id => %d", employeeDto.getJobTitle().getId()))));

        existingEmployee.setDepartment(departmentRepository.findById(employeeDto.getDepartment().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Department not found at id => %d", employeeDto.getDepartment().getId()))));

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return toDto(updatedEmployee);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Attendance Not found at id => %d", id)));
        employeeRepository.setStatusWhereId(employee.getId(), false);
    }

    @Override
    @Transactional
    public void setToActive(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Attendance Not found at id => %d", id)));
        employeeRepository.setStatusWhereId(employee.getId(), true);
    }

    @Override
    public Map<String, Object> getEmployeeDataForExcel(Long employeeId, LocalDate startDate, LocalDate endDate) {
        Map<String,Object> employeeDataMap = new HashMap<>();

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Employee not found at id => %d", employeeId)));

        List<Attendance> employeeAttendance = attendanceRepository.findAllByEmployeeIdAndDateBetween(employeeId,startDate,endDate);
        List<Salary> employeeSalaryList = salaryRepository.findSalariesByEmployeeIdAndDateRange(employeeId,startDate,endDate);

        employeeDataMap.put("employee", employee);
        employeeDataMap.put("attendance", employeeAttendance);
        employeeDataMap.put("salary", employeeSalaryList);

        return employeeDataMap;
    }

    public EmployeeDto toDto(Employee employee){
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .address(employee.getAddress())
                .jobTitle(employee.getJobTitle())
                .dateOfBirth(employee.getDateOfBirth())
                .hireDate(employee.getHireDate())
                .phoneNumber(employee.getPhoneNumber())
                .department(employee.getDepartment())
                .status(employee.getStatus())
                .build();
    }

    public Employee toEntity(EmployeeDto employeeDto){
        return Employee.builder()
                .id(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .address(employeeDto.getAddress())
                .jobTitle(employeeDto.getJobTitle())
                .dateOfBirth(employeeDto.getDateOfBirth())
                .hireDate(employeeDto.getHireDate())
                .phoneNumber(employeeDto.getPhoneNumber())
                .department(employeeDto.getDepartment())
                .status(employeeDto.getStatus())
                .build();
    }
}
