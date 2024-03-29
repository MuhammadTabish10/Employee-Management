package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.model.Department;
import com.example.EmployeeManagement.model.JobTitle;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;

    @NotBlank(message = "First Name cannot be Null")
    private String firstName;

    @NotBlank(message = "Last Name cannot be Null")
    private String lastName;

    @NotBlank(message = "Phone cannot be Null")
    @Pattern(regexp = "^\\d{11}$", message = "Phone number must be 11 digits")
    private String phoneNumber;

    @NotBlank(message = "Address cannot be Null")
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of Birth should be past")
    private LocalDate dateOfBirth;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    private Boolean status;

    @NotNull(message = "Job Title cannot be null")
    private JobTitle jobTitle;

    @NotNull(message = "Department cannot be null")
    private Department department;
}
