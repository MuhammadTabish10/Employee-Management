package com.example.EmployeeManagement.service.impl;

import com.example.EmployeeManagement.exception.RecordNotFoundException;
import com.example.EmployeeManagement.service.JobTitleService;
import com.example.EmployeeManagement.dto.JobTitleDto;
import com.example.EmployeeManagement.model.JobTitle;
import com.example.EmployeeManagement.repository.JobTitleRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobTitleServiceImpl implements JobTitleService {
    private final JobTitleRepository jobTitleRepository;

    public JobTitleServiceImpl(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }

    @Override
    @Transactional
    public JobTitleDto save(JobTitleDto jobTitleDto) {
        JobTitle jobTitle = toEntity(jobTitleDto);
        jobTitle.setStatus(true);
        JobTitle createdJobTitle = jobTitleRepository.save(jobTitle);
        return toDto(createdJobTitle);
    }

    @Override
    public List<JobTitleDto> getAllJobTitle(Boolean status) {
        List<JobTitle> jobTitles= jobTitleRepository.findAllByStatusOrderByIdDesc(status);
        List<JobTitleDto> jobTitlesDto = new ArrayList<>();

        for(JobTitle jobTitle : jobTitles){
            JobTitleDto jobTitleDto = toDto(jobTitle);
            jobTitlesDto.add(jobTitleDto);
        }
        return jobTitlesDto;
    }

    @Override
    public JobTitleDto getJobTitleById(Long id) {
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("JobTitle Not found at id => %d", id)));
        return toDto(jobTitle);
    }

    @Override
    @Transactional
    public JobTitleDto update(Long id, JobTitleDto jobTitleDto) {
        JobTitle existingJobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("JobTitle Not found at id => %d", id)));

        existingJobTitle.setTitle(jobTitleDto.getTitle());

        JobTitle updatedJobTitle = jobTitleRepository.save(existingJobTitle);
        return toDto(updatedJobTitle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("JobTitle Not found at id => %d", id)));
        jobTitleRepository.setStatusWhereId(jobTitle.getId(), false);
    }

    @Override
    @Transactional
    public void setToActive(Long id) {
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("JobTitle Not found at id => %d", id)));
        jobTitleRepository.setStatusWhereId(jobTitle.getId(), true);
    }

    public JobTitleDto toDto(JobTitle jobTitle){
        return JobTitleDto.builder()
                .id(jobTitle.getId())
                .title(jobTitle.getTitle())
                .status(jobTitle.getStatus())
                .build();
    }

    public JobTitle toEntity(JobTitleDto jobTitleDto){
        return JobTitle.builder()
                .id(jobTitleDto.getId())
                .title(jobTitleDto.getTitle())
                .status(jobTitleDto.getStatus())
                .build();
    }
}
