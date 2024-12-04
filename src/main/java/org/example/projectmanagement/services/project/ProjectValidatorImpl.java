package org.example.projectmanagement.services.project;

import org.example.projectmanagement.dtos.ProjectDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

@Component
public class ProjectValidatorImpl implements ProjectValidator {

    private static final Set<String> VALID_METHODOLOGIES = Set.of("Agile", "Kanban", "RUP");

    @Override
    public void validateProjectDto(ProjectDto projectDto) {
        if (projectDto == null) {
            throw new IllegalArgumentException("ProjectDto cannot be null");
        }
        if (!StringUtils.hasText(projectDto.getName())) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }
        if (!VALID_METHODOLOGIES.contains(projectDto.getMethodology())) {
            throw new IllegalArgumentException("Invalid methodology. Valid values are: " + VALID_METHODOLOGIES);
        }
    }

    @Override
    public void validateId(String id) {
        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
    }
}