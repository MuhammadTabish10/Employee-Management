package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByStatus(Boolean status);
    List<Attendance> findAllByEmployee_Id(Long employeeId);

    @Modifying
    @Query("UPDATE Attendance a SET a.status = false WHERE a.id = :id")
    void setStatusToFalseWhereId(Long id);
}
