package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.AttendanceDto;

import java.util.List;

public interface AttendanceService {
    AttendanceDto save(AttendanceDto attendanceDto);
    List<AttendanceDto> getAllAttendance(Boolean status);
    AttendanceDto getAttendanceById(Long id);
    List<AttendanceDto> getAllAttendanceByEmployeeId(Long employeeId);
    AttendanceDto update(Long id ,AttendanceDto attendanceDto);
    String delete(Long id);
}
