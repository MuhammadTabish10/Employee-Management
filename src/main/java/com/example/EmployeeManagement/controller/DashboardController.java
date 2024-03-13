package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/employee/count")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> getEmployeeCount(){
        return ResponseEntity.ok(dashboardService.getTotalEmployeeCount());
    }

    @GetMapping("/department/count")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> getDepartmentCount(){
        return ResponseEntity.ok(dashboardService.getTotalDepartmentCount());
    }

    @GetMapping("/user/count")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> getUserCount(){
        return ResponseEntity.ok(dashboardService.getTotalUserCount());
    }

    @GetMapping("/employee/status/count")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Integer>> getEmployeeStatusCount(){
        return ResponseEntity.ok(dashboardService.getTotalActiveAndInActiveEmployees());
    }

    @GetMapping("/employee/job-title/count")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Integer>> getEmployeeJobTitleCount(){
        return ResponseEntity.ok(dashboardService.getTotalEmployeesAccordingToJobTitle());
    }
}
