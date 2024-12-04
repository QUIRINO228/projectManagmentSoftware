package org.example.projectmanagement.services.task;

import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.models.Task;
import org.springframework.stereotype.Component;

@Component
public interface TaskConverter {
    Task buildTaskFromDto(TaskDto taskDto);
    TaskDto buildTaskDtoFromTask(Task task);
}
