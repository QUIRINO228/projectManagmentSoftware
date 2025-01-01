package org.example.projectmanagement.factory.impl;

import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.factory.ProjectFactory;
import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.models.Team;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

@Component
public class RupProjectFactory implements ProjectFactory {

    @Override
    public Project createProject(ProjectDto projectDto, Object... params) {
        String phase = (String) params[0];
        return Project.builder()
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate())
                .taskIds(new ArrayList<>())
                .methodology("RUP")
                .projectId(projectDto.getProjectId())
                .teamIds(projectDto.getTeamIds())
                .phase(phase)
                .build();
    }

}