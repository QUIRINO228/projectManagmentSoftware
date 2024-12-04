package org.example.projectmanagement.factory;

import org.example.projectmanagement.models.Task;

public interface TaskFactory {
    Task createTask(String projectId, String name, String description, String priority);
}