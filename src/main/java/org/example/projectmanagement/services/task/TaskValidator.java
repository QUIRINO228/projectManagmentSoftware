package org.example.projectmanagement.services.task;

import org.example.projectmanagement.dtos.TaskDto;
import org.springframework.stereotype.Component;

@Component
public interface TaskValidator {
    void validateTaskDto(TaskDto taskDto);
    void validateId(String id);
}
