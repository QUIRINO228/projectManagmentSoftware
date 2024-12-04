package org.example.projectmanagement.services.task;

import org.example.projectmanagement.dtos.TaskDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TaskValidatorImpl implements TaskValidator {

    @Override
    public void validateTaskDto(TaskDto taskDto) {
        if (taskDto == null) {
            throw new IllegalArgumentException("TaskDto cannot be null");
        }
        if (!StringUtils.hasText(taskDto.getName())) {
            throw new IllegalArgumentException("Task name cannot be empty");
        }
    }

    @Override
    public void validateId(String id) {
        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
    }
}