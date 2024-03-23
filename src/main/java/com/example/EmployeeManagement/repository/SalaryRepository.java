package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findAllByStatusOrderByIdDesc(Boolean status);
    @Query("SELECT s FROM Salary s WHERE s.employee.id = :employeeId AND s.createdAt BETWEEN :startDate AND :endDate")
    List<Salary> findSalariesByEmployeeIdAndDateRange(Long employeeId, LocalDate startDate, LocalDate endDate);
    @Modifying
    @Query("UPDATE Salary s SET s.status = :status WHERE s.id = :id")
    void setStatusWhereId(Long id, Boolean status);
}
