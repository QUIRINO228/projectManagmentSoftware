package org.example.projectmanagement.services.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.models.Attachment;
import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.models.Task;
import org.example.projectmanagement.repositories.TaskRepository;
import org.example.projectmanagement.services.attachment.AttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskValidator taskValidator;
    private final TaskConverter taskConverter;
    private final TaskRepository taskRepository;
    private final AttachmentService attachmentService;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        taskValidator.validateTaskDto(taskDto);
        Task task = taskConverter.buildTaskFromDto(taskDto);
        task.setTaskId(UUID.randomUUID().toString());
        return taskConverter.buildTaskDtoFromTask(taskRepository.save(task));
    }
    @Override
    public TaskDto addAttachmentsToTask(String taskId, List<MultipartFile> files) throws IOException {
        TaskDto taskDto = getTaskById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));

        Task task = taskConverter.buildTaskFromDto(taskDto);
        Task savedTask = saveTask(task, files);
        return taskConverter.buildTaskDtoFromTask(savedTask);
    }

    @Override
    public Optional<TaskDto> getTaskById(String taskId) {
        return taskRepository.findById(taskId).map(taskConverter::buildTaskDtoFromTask);
    }

    @Override
    public Task saveTask(Task task, List<MultipartFile> files) throws IOException {
        List<Attachment> attachments = attachmentService.createAttachmentsFromFiles(files);
        attachments.forEach(task::addAttachment);
        return taskRepository.save(task);
    }

    @Override
    public Task saveTask(Task task) {
        log.info("Saving task: {}", task.getStatus());
        return taskRepository.save(task);
    }
}