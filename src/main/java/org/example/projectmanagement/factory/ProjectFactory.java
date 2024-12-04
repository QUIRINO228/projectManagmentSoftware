package org.example.projectmanagement.factory;

import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.models.Task;

public interface ProjectFactory {
    Project createProject(String name, String description, Object... params);

}