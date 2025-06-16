package com.kt.tutorial.dto;

import com.kt.tutorial.model.ProjectStage;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String overview;
    private String architectureDiagram;
    private String businessRequirements;
    private String technicalDocumentation;
    private String apiDocumentation;
    private String setupInstructions;
    private ProjectStage stage;
    private List<Long> assignedEmployeeIds;
    private Long projectManagerId;
} 