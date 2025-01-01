package org.example.projectmanagement.factory.impl;

import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.factory.ProjectFactory;
import org.example.projectmanagement.models.Project;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

@Component
public class KanbanProjectFactory implements ProjectFactory {

    @Override
    public Project createProject(ProjectDto projectDto, Object... params) {
        int workInProgressLimit = (int) params[0];
        return Project.builder()
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate())
                .taskIds(new ArrayList<>())
                .projectId(projectDto.getProjectId())
                .teamIds(projectDto.getTeamIds())
                .methodology("Kanban")
                .workInProgressLimit(workInProgressLimit)
                .build();
    }

}