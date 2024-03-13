package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findAllByStatus(Boolean status);

    @Modifying
    @Query("UPDATE Salary s SET s.status = :status WHERE s.id = :id")
    void setStatusWhereId(Long id, Boolean status);
}
