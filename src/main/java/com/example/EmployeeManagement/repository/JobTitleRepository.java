package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.model.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
    List<JobTitle> findAllByStatus(Boolean status);

    @Modifying
    @Query("UPDATE JobTitle jt SET jt.status = false WHERE jt.id = :id")
    void setStatusToFalseWhereId(Long id);
}
