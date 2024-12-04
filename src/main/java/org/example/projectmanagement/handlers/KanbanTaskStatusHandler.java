package org.example.projectmanagement.handlers;

import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.models.Task;
import org.springframework.stereotype.Component;

@Component
public class KanbanTaskStatusHandler implements TaskStatusHandler {
    private TaskStatusHandler nextHandler;

    @Override
    public void setNextHandler(TaskStatusHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleTaskStatus(Project project, Task task, String newStatus) {
        if ("Kanban".equals(project.getMethodology())) {
            if ("IN_PROGRESS".equals(newStatus)) {
                project.setWorkInProgressLimit(project.getWorkInProgressLimit() - 1);
            }
        } else if (nextHandler != null) {
            nextHandler.handleTaskStatus(project, task, newStatus);
        }
    }
}