package com.kt.tutorial.service;

import com.kt.tutorial.dto.ProjectDTO;
import com.kt.tutorial.model.Project;
import com.kt.tutorial.model.ProjectStage;
import com.kt.tutorial.model.User;
import com.kt.tutorial.repository.ProjectRepository;
import com.kt.tutorial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setOverview(projectDTO.getOverview());
        project.setArchitectureDiagram(projectDTO.getArchitectureDiagram());
        project.setBusinessRequirements(projectDTO.getBusinessRequirements());
        project.setTechnicalDocumentation(projectDTO.getTechnicalDocumentation());
        project.setApiDocumentation(projectDTO.getApiDocumentation());
        project.setSetupInstructions(projectDTO.getSetupInstructions());
        project.setStage(projectDTO.getStage());

        if (projectDTO.getProjectManagerId() != null) {
            User manager = userRepository.findById(projectDTO.getProjectManagerId())
                    .orElseThrow(() -> new RuntimeException("Project manager not found"));
            project.setProjectManager(manager);
        }

        if (projectDTO.getAssignedEmployeeIds() != null) {
            List<User> employees = projectDTO.getAssignedEmployeeIds().stream()
                    .map(id -> userRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Employee not found: " + id)))
                    .collect(Collectors.toList());
            project.setAssignedEmployees(employees);
        }

        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return convertToDTO(project);
    }

    @Transactional
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(projectDTO.getName());
        project.setOverview(projectDTO.getOverview());
        project.setArchitectureDiagram(projectDTO.getArchitectureDiagram());
        project.setBusinessRequirements(projectDTO.getBusinessRequirements());
        project.setTechnicalDocumentation(projectDTO.getTechnicalDocumentation());
        project.setApiDocumentation(projectDTO.getApiDocumentation());
        project.setSetupInstructions(projectDTO.getSetupInstructions());
        project.setStage(projectDTO.getStage());

        if (projectDTO.getProjectManagerId() != null) {
            User manager = userRepository.findById(projectDTO.getProjectManagerId())
                    .orElseThrow(() -> new RuntimeException("Project manager not found"));
            project.setProjectManager(manager);
        }

        if (projectDTO.getAssignedEmployeeIds() != null) {
            List<User> employees = projectDTO.getAssignedEmployeeIds().stream()
                    .map(employeeId -> userRepository.findById(employeeId)
                            .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId)))
                    .collect(Collectors.toList());
            project.setAssignedEmployees(employees);
        }

        Project updatedProject = projectRepository.save(project);
        return convertToDTO(updatedProject);
    }

    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setOverview(project.getOverview());
        dto.setArchitectureDiagram(project.getArchitectureDiagram());
        dto.setBusinessRequirements(project.getBusinessRequirements());
        dto.setTechnicalDocumentation(project.getTechnicalDocumentation());
        dto.setApiDocumentation(project.getApiDocumentation());
        dto.setSetupInstructions(project.getSetupInstructions());
        dto.setStage(project.getStage());
        
        if (project.getProjectManager() != null) {
            dto.setProjectManagerId(project.getProjectManager().getId());
        }
        
        if (project.getAssignedEmployees() != null) {
            dto.setAssignedEmployeeIds(project.getAssignedEmployees().stream()
                    .map(User::getId)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
} 