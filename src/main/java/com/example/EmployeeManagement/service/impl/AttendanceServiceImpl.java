package com.example.EmployeeManagement.service.impl;

import com.example.EmployeeManagement.exception.RecordNotFoundException;
import com.example.EmployeeManagement.dto.AttendanceDto;
import com.example.EmployeeManagement.model.Attendance;
import com.example.EmployeeManagement.repository.AttendanceRepository;
import com.example.EmployeeManagement.repository.EmployeeRepository;
import com.example.EmployeeManagement.service.AttendanceService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public AttendanceDto save(AttendanceDto attendanceDto) {
        Attendance attendance = toEntity(attendanceDto);
        attendance.setStatus(true);

        attendance.setEmployee(employeeRepository.findById(attendance.getEmployee().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Employee not found at id => %d", attendance.getEmployee().getId()))));

        return toDto(attendanceRepository.save(attendance));
    }

    @Override
    public List<AttendanceDto> getAllAttendance(Boolean status) {
        List<Attendance> attendanceList = attendanceRepository.findAllByStatus(status);
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();

        for(Attendance attendance : attendanceList){
            AttendanceDto attendanceDto = toDto(attendance);
            attendanceDtoList.add(attendanceDto);
        }
        return attendanceDtoList;
    }

    @Override
    public AttendanceDto getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Attendance Not found at id => %d", id)));
        return toDto(attendance);
    }

    @Override
    public List<AttendanceDto> getAllAttendanceByEmployeeId(Long employeeId) {
        List<Attendance> attendanceList = attendanceRepository.findAllByEmployee_Id(employeeId);
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();

        for(Attendance attendance : attendanceList){
            AttendanceDto attendanceDto = toDto(attendance);
            attendanceDtoList.add(attendanceDto);
        }
        return attendanceDtoList;
    }

    @Override
    @Transactional
    public AttendanceDto update(Long id, AttendanceDto attendanceDto) {
        Attendance existingAttendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Attendance Not found at id => %d", id)));

        existingAttendance.setDate(attendanceDto.getDate());
        existingAttendance.setTimeIn(attendanceDto.getTimeIn());
        existingAttendance.setTimeOut(attendanceDto.getTimeOut());
        existingAttendance.setAttendanceStatus(attendanceDto.getAttendanceStatus());

        existingAttendance.setEmployee(employeeRepository.findById(attendanceDto.getEmployee().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Employee not found at id => %d", attendanceDto.getEmployee().getId()))));

        Attendance updatedAttendance = attendanceRepository.save(existingAttendance);
        return toDto(updatedAttendance);
    }

    @Override
    @Transactional
    public String delete(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Attendance Not found at id => %d", id)));
        attendanceRepository.setStatusToFalseWhereId(attendance.getId());
        return String.format("Attendance Deleted at id => %d", id);
    }

    public AttendanceDto toDto(Attendance attendance){
        return AttendanceDto.builder()
                .id(attendance.getId())
                .date(attendance.getDate())
                .timeIn(attendance.getTimeIn())
                .timeOut(attendance.getTimeOut())
                .attendanceStatus(attendance.getAttendanceStatus())
                .employee(attendance.getEmployee())
                .status(attendance.getStatus())
                .build();
    }

    public Attendance toEntity(AttendanceDto attendanceDto){
        return Attendance.builder()
                .id(attendanceDto.getId())
                .date(attendanceDto.getDate())
                .timeIn(attendanceDto.getTimeIn())
                .timeOut(attendanceDto.getTimeOut())
                .attendanceStatus(attendanceDto.getAttendanceStatus())
                .employee(attendanceDto.getEmployee())
                .status(attendanceDto.getStatus())
                .build();
    }
}
