package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.model.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @Positive(message = "Amount should be positive")
    private Double amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Boolean status;

    @NotNull(message = "Employee cannot be null")
    private Employee employee;
}
