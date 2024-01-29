package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByStatus(Boolean status);

    List<Employee> findAllByJobTitle_Id(Long jobTitleId);
    List<Employee> findAllByDepartment_Id(Long departmentId);

    @Modifying
    @Query("UPDATE Employee e SET e.status = false WHERE e.id = :id")
    void setStatusToFalseWhereId(Long id);
}
