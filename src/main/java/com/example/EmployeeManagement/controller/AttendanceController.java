package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.AttendanceDto;
import com.example.EmployeeManagement.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/attendance")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttendanceDto> createAttendance(@Valid @RequestBody AttendanceDto attendanceDto){
        AttendanceDto attendance = attendanceService.save(attendanceDto);
        return ResponseEntity.ok(attendance);
    }
    @GetMapping("/attendance/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AttendanceDto>> getAllAttendance(@PathVariable Boolean status){
        List<AttendanceDto> attendances = attendanceService.getAllAttendance(status);
        return ResponseEntity.ok(attendances);
    }
    @GetMapping("/attendance/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttendanceDto> getAttendanceById(@PathVariable Long id){
        AttendanceDto attendance = attendanceService.getAttendanceById(id);
        return ResponseEntity.ok(attendance);
    }
    @PutMapping("/attendance/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttendanceDto> updateAttendance(@Valid @PathVariable Long id, @RequestBody AttendanceDto attendanceDto){
        AttendanceDto attendance = attendanceService.update(id, attendanceDto);
        return ResponseEntity.ok(attendance);
    }
    @DeleteMapping("/attendance/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteAttendanceById(@PathVariable Long id){
        return ResponseEntity.ok(attendanceService.delete(id));
    }
}
