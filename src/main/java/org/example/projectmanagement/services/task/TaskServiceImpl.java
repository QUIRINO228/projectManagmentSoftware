package org.example.projectmanagement.services.task;

import lombok.AllArgsConstructor;
import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.models.Attachment;
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
    public Optional<Task> getTaskById(String taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public Task saveTask(Task task, List<MultipartFile> files) throws IOException {
        List<Attachment> attachments = attachmentService.createAttachmentsFromFiles(files);
        attachments.forEach(task::addAttachment);
        return taskRepository.save(task);
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }
}