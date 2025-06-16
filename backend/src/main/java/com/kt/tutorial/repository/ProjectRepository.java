package com.kt.tutorial.repository;

import com.kt.tutorial.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByProjectManagerId(Long projectManagerId);
    List<Project> findByAssignedEmployeesId(Long employeeId);
} 