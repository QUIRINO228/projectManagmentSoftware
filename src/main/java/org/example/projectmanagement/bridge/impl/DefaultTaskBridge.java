package org.example.projectmanagement.bridge.impl;

import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.models.Task;
import org.example.projectmanagement.bridge.TaskBridge;

import java.util.HashSet;
import java.util.UUID;

public class DefaultTaskBridge implements TaskBridge {

    @Override
    public Task createTask(TaskDto taskDto, String initialStatus, String methodology) {
        return Task.builder()
                .name(taskDto.getName())
                .projectId(taskDto.getProjectId())
                .description(taskDto.getDescription())
                .status(initialStatus)
                .assignee(taskDto.getAssignee())
                .methodology(methodology)
                .priority(taskDto.getPriority())
                .dueDate(taskDto.getDueDate())
                .attachments(new HashSet<>())
                .build();
    }

    @Override
    public String getInitialStatus() {
        return null;
    }
}