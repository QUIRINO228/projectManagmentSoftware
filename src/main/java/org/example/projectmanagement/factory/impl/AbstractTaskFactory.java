package org.example.projectmanagement.factory.impl;

import org.example.projectmanagement.factory.TaskFactory;
import org.example.projectmanagement.models.Task;

public abstract class AbstractTaskFactory implements TaskFactory {
    protected String methodology;

    @Override
    public Task createTask(String projectId, String name, String description, String priority) {
        return Task.builder()
                .projectId(projectId)
                .name(name)
                .description(description)
                .priority(priority)
                .status(getInitialStatus())
                .methodology(methodology)
                .build();
    }

    protected abstract String getInitialStatus();
}