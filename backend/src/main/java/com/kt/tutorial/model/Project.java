package com.kt.tutorial.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "projects")
@EntityListeners(AuditingEntityListener.class)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(columnDefinition = "TEXT")
    private String architectureDiagram;

    @Column(columnDefinition = "TEXT")
    private String businessRequirements;

    @Column(columnDefinition = "TEXT")
    private String technicalDocumentation;

    @Column(columnDefinition = "TEXT")
    private String apiDocumentation;

    @Column(columnDefinition = "TEXT")
    private String setupInstructions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStage stage;

    @ManyToMany
    @JoinTable(
        name = "project_employees",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> assignedEmployees = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_manager_id", nullable = false)
    private User projectManager;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getArchitectureDiagram() {
        return architectureDiagram;
    }

    public void setArchitectureDiagram(String architectureDiagram) {
        this.architectureDiagram = architectureDiagram;
    }

    public String getBusinessRequirements() {
        return businessRequirements;
    }

    public void setBusinessRequirements(String businessRequirements) {
        this.businessRequirements = businessRequirements;
    }

    public String getTechnicalDocumentation() {
        return technicalDocumentation;
    }

    public void setTechnicalDocumentation(String technicalDocumentation) {
        this.technicalDocumentation = technicalDocumentation;
    }

    public String getApiDocumentation() {
        return apiDocumentation;
    }

    public void setApiDocumentation(String apiDocumentation) {
        this.apiDocumentation = apiDocumentation;
    }

    public String getSetupInstructions() {
        return setupInstructions;
    }

    public void setSetupInstructions(String setupInstructions) {
        this.setupInstructions = setupInstructions;
    }

    public ProjectStage getStage() {
        return stage;
    }

    public void setStage(ProjectStage stage) {
        this.stage = stage;
    }

    public List<User> getAssignedEmployees() {
        return assignedEmployees;
    }

    public void setAssignedEmployees(List<User> assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }

    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 