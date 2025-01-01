package org.example.projectmanagement.factory;

import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.models.Task;
import org.example.projectmanagement.models.Team;

import java.util.Set;

public interface ProjectFactory {
    Project createProject(ProjectDto projectDto, Object... params);

}