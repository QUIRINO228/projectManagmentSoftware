package org.example.projectmanagement.handlers;

import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.models.Task;

public interface TaskStatusHandler {
    void setNextHandler(TaskStatusHandler nextHandler);
    void handleTaskStatus(Project project, Task task, String newStatus);
}