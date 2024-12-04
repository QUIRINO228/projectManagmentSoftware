package org.example.projectmanagement.services.project;

import org.example.projectmanagement.dtos.ProjectDto;
import org.springframework.stereotype.Component;

@Component
public interface ProjectValidator {
    void validateProjectDto(ProjectDto projectDto);

    void validateId(String id);
}