package com.kt.tutorial.service;

import com.kt.tutorial.dto.ProjectDTO;
import com.kt.tutorial.model.Project;
import com.kt.tutorial.model.ProjectStage;
import com.kt.tutorial.repository.ProjectRepository;
import com.kt.tutorial.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void createProject_WithNullManagerAndEmployees_ShouldCreateProject() {
        ProjectDTO dto = new ProjectDTO();
        dto.setName("Test Project");
        dto.setOverview("Overview");
        dto.setStage(ProjectStage.PLANNING);
        // projectManagerId and assignedEmployeeIds are null

        Project project = new Project();
        project.setId(1L);
        project.setName(dto.getName());
        project.setOverview(dto.getOverview());
        project.setStage(dto.getStage());

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectDTO result = projectService.createProject(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getOverview(), result.getOverview());
        assertEquals(dto.getStage(), result.getStage());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void createProject_WithInvalidProjectManagerId_ShouldThrowException() {
        ProjectDTO dto = new ProjectDTO();
        dto.setName("Test Project");
        dto.setProjectManagerId(999L); // Invalid ID

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectService.createProject(dto));
    }

    @Test
    void createProject_WithInvalidAssignedEmployeeId_ShouldThrowException() {
        ProjectDTO dto = new ProjectDTO();
        dto.setName("Test Project");
        dto.setAssignedEmployeeIds(List.of(999L)); // Invalid ID

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectService.createProject(dto));
    }

    @Test
    void updateProject_WithNullManagerAndEmployees_ShouldUpdateProject() {
        Long projectId = 1L;
        Project existingProject = new Project();
        existingProject.setId(projectId);
        existingProject.setName("Old Name");
        existingProject.setOverview("Old Overview");
        existingProject.setStage(ProjectStage.PLANNING);

        ProjectDTO dto = new ProjectDTO();
        dto.setName("Updated Project");
        dto.setOverview("Updated Overview");
        dto.setStage(ProjectStage.PLANNING);
        // projectManagerId and assignedEmployeeIds are null

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(any(Project.class))).thenReturn(existingProject);

        ProjectDTO result = projectService.updateProject(projectId, dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getOverview(), result.getOverview());
        assertEquals(dto.getStage(), result.getStage());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void updateProject_WithInvalidProjectManagerId_ShouldThrowException() {
        Long projectId = 1L;
        Project existingProject = new Project();
        existingProject.setId(projectId);

        ProjectDTO dto = new ProjectDTO();
        dto.setName("Updated Project");
        dto.setProjectManagerId(999L); // Invalid ID

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectService.updateProject(projectId, dto));
    }

    @Test
    void updateProject_WithInvalidAssignedEmployeeId_ShouldThrowException() {
        Long projectId = 1L;
        Project existingProject = new Project();
        existingProject.setId(projectId);

        ProjectDTO dto = new ProjectDTO();
        dto.setName("Updated Project");
        dto.setAssignedEmployeeIds(List.of(999L)); // Invalid ID

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectService.updateProject(projectId, dto));
    }

    @Test
    void deleteProject_WithNonExistentId_ShouldThrowException() {
        // Arrange
        Long nonExistentId = 999L;
        when(projectRepository.existsById(nonExistentId)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> projectService.deleteProject(nonExistentId));
        verify(projectRepository, never()).deleteById(any());
    }
} 