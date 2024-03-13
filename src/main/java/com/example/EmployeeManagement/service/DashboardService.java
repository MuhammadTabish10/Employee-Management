package com.example.EmployeeManagement.service;

import java.util.Map;

public interface DashboardService {
    Integer getTotalEmployeeCount();
    Integer getTotalDepartmentCount();
    Integer getTotalUserCount();
    Map<String,Integer> getTotalActiveAndInActiveEmployees();
    Map<String,Integer> getTotalEmployeesAccordingToJobTitle();
}
