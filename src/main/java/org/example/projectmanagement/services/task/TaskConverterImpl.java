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
                .projectId(taskDto.getProjectId())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus())
                .assignee(taskDto.getAssignee())
                .methodology(taskDto.getMethodology())
                .priority(taskDto.getPriority())
                .dueDate(taskDto.getDueDate())
                .attachments(taskDto.getAttachments())
                .build();
    }

    @Override
    public TaskDto buildTaskDtoFromTask(Task task) {
        return TaskDto.builder()
                .taskId(task.getTaskId())
                .name(task.getName())
                .projectId(task.getProjectId())
                .description(task.getDescription())
                .status(task.getStatus())
                .assignee(task.getAssignee())
                .methodology(task.getMethodology())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .attachments(task.getAttachments())
                .build();
    }
}