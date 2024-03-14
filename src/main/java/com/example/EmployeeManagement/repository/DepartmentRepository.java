package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByStatusOrderByIdDesc(Boolean status);

    @Modifying
    @Query("UPDATE Department d SET d.status = :status WHERE d.id = :id")
    void setStatusWhereId(Long id, Boolean status);
}
