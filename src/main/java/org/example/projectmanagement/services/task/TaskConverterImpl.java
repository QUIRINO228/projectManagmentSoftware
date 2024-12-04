package org.example.projectmanagement.services.task;

import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.models.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskConverterImpl implements TaskConverter {

    @Override
    public Task buildTaskFromDto(TaskDto taskDto) {
        return Task.builder()
                .taskId(taskDto.getTaskId())
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .build();
    }

    @Override
    public TaskDto buildTaskDtoFromTask(Task task) {
        return TaskDto.builder()
                .taskId(task.getTaskId())
                .name(task.getName())
                .description(task.getDescription())
                .build();
    }
}