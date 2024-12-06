package org.example.projectmanagement.bridge;

import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.models.Task;

public interface TaskBridge {
    Task createTask(TaskDto taskDto, String initialStatus, String methodology);
    String getInitialStatus();
}
