package org.example.projectmanagement.factory.impl;

import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.factory.ProjectFactory;
import org.example.projectmanagement.models.Project;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

@Component
@Primary
public class AgileProjectFactory implements ProjectFactory {

    @Override
    public Project createProject(ProjectDto projectDto, Object... params) {
        int sprintDuration = (int) params[0];
        return Project.builder()
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate())
                .taskIds(new ArrayList<>())
                .methodology("Agile")
                .projectId(projectDto.getProjectId())
                .teamIds(projectDto.getTeamIds())
                .sprintDuration(sprintDuration)
                .currentSprint(0)
                .build();
    }

}