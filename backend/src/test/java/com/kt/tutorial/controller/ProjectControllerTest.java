package com.kt.tutorial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.tutorial.config.TestSecurityConfig;
import com.kt.tutorial.dto.ProjectDTO;
import com.kt.tutorial.model.ProjectStage;
import com.kt.tutorial.service.ProjectService;
import com.kt.tutorial.security.CustomUserDetailsService;
import com.kt.tutorial.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
@Import(TestSecurityConfig.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @WithMockUser
    public void testCreateProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Test Project");
        projectDTO.setOverview("Test Overview");
        projectDTO.setStage(ProjectStage.PLANNING);

        when(projectService.createProject(any(ProjectDTO.class))).thenReturn(projectDTO);

        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Project"))
                .andExpect(jsonPath("$.overview").value("Test Overview"))
                .andExpect(jsonPath("$.stage").value("PLANNING"));
    }

    @Test
    @WithMockUser
    public void testGetProjectById() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Test Project");
        projectDTO.setStage(ProjectStage.PLANNING);

        when(projectService.getProjectById(1L)).thenReturn(projectDTO);

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Project"))
                .andExpect(jsonPath("$.stage").value("PLANNING"));
    }

    @Test
    @WithMockUser
    public void testGetAllProjects() throws Exception {
        ProjectDTO project1 = new ProjectDTO();
        project1.setId(1L);
        project1.setName("Project 1");
        ProjectDTO project2 = new ProjectDTO();
        project2.setId(2L);
        project2.setName("Project 2");

        List<ProjectDTO> projects = Arrays.asList(project1, project2);

        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Project 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Project 2"));
    }

    @Test
    @WithMockUser
    public void testUpdateProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Updated Project");
        projectDTO.setOverview("Updated Overview");

        when(projectService.updateProject(eq(1L), any(ProjectDTO.class))).thenReturn(projectDTO);

        mockMvc.perform(put("/api/projects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Project"))
                .andExpect(jsonPath("$.overview").value("Updated Overview"));
    }

    @Test
    @WithMockUser
    public void testDeleteProject() throws Exception {
        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isNoContent());
    }
} 