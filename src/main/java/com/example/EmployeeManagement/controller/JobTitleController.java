package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.service.JobTitleService;
import com.example.EmployeeManagement.dto.JobTitleDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JobTitleController {
    private final JobTitleService jobTitleService;

    public JobTitleController(JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    @PostMapping("/job-title")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<JobTitleDto> createJobTitle(@Valid @RequestBody JobTitleDto jobTitleDto){
        JobTitleDto jobTitle = jobTitleService.save(jobTitleDto);
        return ResponseEntity.ok(jobTitle);
    }
    @GetMapping("/job-title/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<List<JobTitleDto>> getAllJobTitles(@PathVariable Boolean status){
        List<JobTitleDto> jobTitleDtoList = jobTitleService.getAllJobTitle(status);
        return ResponseEntity.ok(jobTitleDtoList);
    }
    @GetMapping("/job-title/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<JobTitleDto> getJobTitleById(@PathVariable Long id){
        JobTitleDto jobTitleDto = jobTitleService.getJobTitleById(id);
        return ResponseEntity.ok(jobTitleDto);
    }
    @PutMapping("/job-title/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<JobTitleDto> updateJobTitle(@Valid @PathVariable Long id, @RequestBody JobTitleDto jobTitleDto){
        JobTitleDto jobTitle = jobTitleService.update(id,jobTitleDto);
        return ResponseEntity.ok(jobTitle);
    }
    @DeleteMapping("/job-title/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<String> deleteJobTitleById(@PathVariable Long id){
        return ResponseEntity.ok(jobTitleService.delete(id));
    }
}
