package org.example.projectmanagement.factory.impl;

import org.example.projectmanagement.factory.ProjectFactory;
import org.example.projectmanagement.models.Project;

import java.time.LocalDate;
import java.util.ArrayList;

public class RupProjectFactory implements ProjectFactory {

    @Override
    public Project createProject(String name, String description, Object... params) {
        String phase = (String) params[0];
        return Project.builder()
                .name(name)
                .description(description)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusYears(1))
                .taskIds(new ArrayList<>())
                .methodology("RUP")
                .phase(phase)
                .build();
    }

}