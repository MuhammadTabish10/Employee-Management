package com.example.EmployeeManagement.service.impl;

import com.example.EmployeeManagement.model.Employee;
import com.example.EmployeeManagement.repository.DepartmentRepository;
import com.example.EmployeeManagement.repository.EmployeeRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import com.example.EmployeeManagement.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Integer getTotalEmployeeCount() {
        return (int) employeeRepository.count();
    }

    @Override
    public Integer getTotalDepartmentCount() {
        return (int) departmentRepository.count();
    }

    @Override
    public Integer getTotalUserCount() {
        return (int) userRepository.count();
    }

    @Override
    public Map<String, Integer> getTotalActiveAndInActiveEmployees() {
        Map<String,Integer> activeAndInactiveMap = new HashMap<>();
        activeAndInactiveMap.put("Active", employeeRepository.countByStatusTrue());
        activeAndInactiveMap.put("InActive", employeeRepository.countByStatusFalse());
        return activeAndInactiveMap;
    }

    @Override
    public Map<String, Integer> getTotalEmployeesAccordingToJobTitle() {
        Map<String,Integer> jobEmployeeMap = new HashMap<>();
        List<Employee> employeeList = employeeRepository.findAllByStatusOrderByIdDesc(true);
        for(Employee employee : employeeList){
            String jobTitle = employee.getJobTitle().getTitle();
            jobEmployeeMap.put(jobTitle, jobEmployeeMap.getOrDefault(jobTitle,0) + 1);
        }
        return jobEmployeeMap;
    }
}
