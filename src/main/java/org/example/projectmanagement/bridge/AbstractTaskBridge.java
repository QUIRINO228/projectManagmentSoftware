package org.example.projectmanagement.bridge;

import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.bridge.TaskBridge;
import org.example.projectmanagement.models.Task;

public abstract class AbstractTaskBridge implements TaskBridge {
    protected String methodology;

    @Override
    public Task createTask(TaskDto taskDto) {
        return Task.builder()
                .projectId(taskDto.getProjectId())
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .priority(taskDto.getPriority())
                .status(getInitialStatus())
                .methodology(methodology)
                .build();
    }

    protected abstract String getInitialStatus();
}