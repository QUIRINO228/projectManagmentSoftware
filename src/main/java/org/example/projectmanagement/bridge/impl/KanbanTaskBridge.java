package org.example.projectmanagement.bridge.impl;

import org.example.projectmanagement.bridge.AbstractTaskBridge;
import org.example.projectmanagement.bridge.TaskBridge;
import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.models.Task;
import org.example.projectmanagement.models.enums.KanbanColumn;

public class KanbanTaskBridge extends AbstractTaskBridge {

    public KanbanTaskBridge(TaskBridge taskBridge) {
        super("Kanban", taskBridge);
    }

    @Override
    public String getInitialStatus() {
        return KanbanColumn.BACKLOG.name();
    }

    @Override
    public Task createTask(TaskDto taskDto, String initialStatus, String methodology) {
        return taskBridge.createTask(taskDto, initialStatus, methodology);
    }
}