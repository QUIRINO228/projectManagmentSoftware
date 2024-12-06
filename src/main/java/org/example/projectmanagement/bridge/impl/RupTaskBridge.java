package org.example.projectmanagement.bridge.impl;

import org.example.projectmanagement.bridge.AbstractTaskBridge;
import org.example.projectmanagement.bridge.TaskBridge;
import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.models.Task;
import org.example.projectmanagement.models.enums.RUPPhase;

public class RupTaskBridge extends AbstractTaskBridge {

    public RupTaskBridge(TaskBridge taskBridge) {
        super("RUP", taskBridge);
    }

    @Override
    public String getInitialStatus() {
        return RUPPhase.INCEPTION.name();
    }

    @Override
    public Task createTask(TaskDto taskDto, String initialStatus, String methodology) {
        return taskBridge.createTask(taskDto, initialStatus, methodology);
    }
}