package org.example.projectmanagement.factory.impl;

import org.example.projectmanagement.factory.ProjectFactory;
import org.example.projectmanagement.models.Project;

import java.time.LocalDate;
import java.util.ArrayList;

public class KanbanProjectFactory implements ProjectFactory {

    @Override
    public Project createProject(String name, String description, Object... params) {
        int workInProgressLimit = (int) params[0];
        return Project.builder()
                .name(name)
                .description(description)
                .startDate(LocalDate.now())
                .taskIds(new ArrayList<>())
                .methodology("Kanban")
                .workInProgressLimit(workInProgressLimit)
                .build();
    }

}