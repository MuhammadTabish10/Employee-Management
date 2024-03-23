package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.model.Attendance;
import com.example.EmployeeManagement.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByStatusOrderByIdDesc(Boolean status);
    List<Attendance> findAllByEmployee_Id(Long employeeId);
    @Query("SELECT a FROM Attendance a WHERE a.employee.id = :employeeId AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findAllByEmployeeIdAndDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
    @Modifying
    @Query("UPDATE Attendance a SET a.status = :status WHERE a.id = :id")
    void setStatusWhereId(Long id, Boolean status);
}
