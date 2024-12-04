package org.example.projectmanagement.factory.impl;

import org.example.projectmanagement.factory.ProjectFactory;
import org.example.projectmanagement.models.Project;

import java.time.LocalDate;
import java.util.ArrayList;

public class AgileProjectFactory implements ProjectFactory {

    @Override
    public Project createProject(String name, String description, Object... params) {
        int sprintDuration = (int) params[0];
        return Project.builder()
                .name(name)
                .description(description)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .taskIds(new ArrayList<>())
                .methodology("Agile")
                .sprintDuration(sprintDuration)
                .currentSprint(0)
                .build();
    }

}