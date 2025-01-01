package org.example.projectmanagement.services.task;

import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.models.Task;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface TaskService {
    Task saveTask(Task task, List<MultipartFile> files) throws IOException;
    Task saveTask(Task task);
    TaskDto createTask(TaskDto taskDto);
    Optional<TaskDto> getTaskById(String taskId);

    TaskDto addAttachmentsToTask(String taskId, List<MultipartFile> files) throws IOException;

}
