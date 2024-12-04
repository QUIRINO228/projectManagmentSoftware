package org.example.projectmanagement.services.task;

import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.models.Task;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface TaskService {
    Task saveTask(Task task, List<MultipartFile> files) throws IOException;

    void saveTask(Task task);

    TaskDto createTask(TaskDto taskDto);

    Optional<Task> getTaskById(String taskId);
}
