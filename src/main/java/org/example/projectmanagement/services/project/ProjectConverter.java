package org.example.projectmanagement.services.project;

import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.models.Project;
import org.springframework.stereotype.Component;

@Component
public interface ProjectConverter {
    Project buildProjectFromDto(ProjectDto projectDto);
    ProjectDto buildProjectDtoFromProject(Project project);
}