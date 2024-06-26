package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.AttendanceDto;
import com.example.EmployeeManagement.service.AttendanceService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/attendance")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<AttendanceDto> createAttendance(@Valid @RequestBody AttendanceDto attendanceDto){
        AttendanceDto attendance = attendanceService.save(attendanceDto);
        return ResponseEntity.ok(attendance);
    }
    @GetMapping("/attendance/status/{status}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<List<AttendanceDto>> getAllAttendance(@PathVariable Boolean status){
        List<AttendanceDto> attendances = attendanceService.getAllAttendance(status);
        return ResponseEntity.ok(attendances);
    }
    @GetMapping("/attendance/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<AttendanceDto> getAttendanceById(@PathVariable Long id){
        AttendanceDto attendance = attendanceService.getAttendanceById(id);
        return ResponseEntity.ok(attendance);
    }
    @PutMapping("/attendance/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<AttendanceDto> updateAttendance(@PathVariable Long id, @Valid @RequestBody AttendanceDto attendanceDto){
        AttendanceDto attendance = attendanceService.update(id, attendanceDto);
        return ResponseEntity.ok(attendance);
    }
    @DeleteMapping("/attendance/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<Void> deleteAttendanceById(@PathVariable Long id){
        attendanceService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/attendance/{id}/status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<Void> setAttendanceStatusToActiveById(@PathVariable Long id){
        attendanceService.setToActive(id);
        return ResponseEntity.ok().build();
    }
}
