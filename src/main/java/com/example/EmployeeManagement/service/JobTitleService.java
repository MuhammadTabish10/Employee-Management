package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.JobTitleDto;

import java.util.List;

public interface JobTitleService {
    JobTitleDto save(JobTitleDto jobTitleDto);
    List<JobTitleDto> getAllJobTitle(Boolean status);
    JobTitleDto getJobTitleById(Long id);
    JobTitleDto update(Long id, JobTitleDto jobTitleDto);
    void delete(Long id);
    void setToActive(Long id);
}
