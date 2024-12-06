package org.example.projectmanagement.bridge;

import lombok.AllArgsConstructor;
import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.models.Task;

@AllArgsConstructor
public abstract class AbstractTaskBridge implements TaskBridge {
    protected String methodology;
    protected TaskBridge taskBridge;


    public Task createTask(TaskDto taskDto) {
        return taskBridge.createTask(taskDto, getInitialStatus(), methodology);
    }

    @Override
    public abstract String getInitialStatus();
}