package org.example.projectmanagement.handlers;

import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.models.Task;
import org.springframework.stereotype.Component;

@Component
public class RupTaskStatusHandler implements TaskStatusHandler {
    private TaskStatusHandler nextHandler;

    @Override
    public void setNextHandler(TaskStatusHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleTaskStatus(Project project, Task task, String newStatus) {
        if ("RUP".equals(project.getMethodology())) {
            if ("COMPLETED".equals(newStatus)) {
                project.setPhase("Next Phase");
            }
        } else if (nextHandler != null) {
            nextHandler.handleTaskStatus(project, task, newStatus);
        }
    }
}