package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.enums.AttendanceStatus;
import com.example.EmployeeManagement.model.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "TimeIn cannot be null")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time timeIn;

    @NotNull(message = "TimeOut cannot be null")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time timeOut;

    private Boolean status;

    @NotNull(message = "Attendance Status cannot be null")
    private AttendanceStatus attendanceStatus;

    @NotNull(message = "Employee cannot be null")
    private Employee employee;
}
