package org.example.projectmanagement.services.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.bridge.TaskBridge;
import org.example.projectmanagement.bridge.TaskBridgeProvider;
import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.factory.ProjectFactory;
import org.example.projectmanagement.factory.ProjectFactoryProvider;
import org.example.projectmanagement.handlers.TaskStatusHandler;
import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.models.Task;
import org.example.projectmanagement.repositories.ProjectRepository;
import org.example.projectmanagement.services.task.TaskConverter;
import org.example.projectmanagement.services.task.TaskService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectValidator projectValidator;
    private final ProjectConverter projectConverter;
    private final TaskConverter taskConverter;
    private final TaskService taskService;
    private final TaskStatusHandler taskStatusHandler;
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ProjectValidator projectValidator,
                              ProjectConverter projectConverter,
                              TaskConverter taskConverter,
                              TaskService taskService,
                              @Qualifier("agileTaskStatusHandler") TaskStatusHandler taskStatusHandler) {
        this.projectRepository = projectRepository;
        this.projectValidator = projectValidator;
        this.projectConverter = projectConverter;
        this.taskConverter = taskConverter;
        this.taskService = taskService;
        this.taskStatusHandler = taskStatusHandler;
    }
    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        projectValidator.validateProjectDto(projectDto);
        ProjectFactory projectFactory = ProjectFactoryProvider.getFactory(projectDto.getMethodology());
        Project project = createProjectWithMethodology(projectFactory, projectDto);
        return projectConverter.buildProjectDtoFromProject(saveProject(project));
    }

    @Override
    public ProjectDto updateProject(String id, ProjectDto projectDto) {
        projectValidator.validateId(id);
        projectValidator.validateProjectDto(projectDto);
        ProjectFactory projectFactory = ProjectFactoryProvider.getFactory(projectDto.getMethodology());
        Project project = createProjectWithMethodology(projectFactory, projectDto);
        project.setProjectId(id);
        return projectConverter.buildProjectDtoFromProject(saveProject(project));
    }

    @Override
    public Optional<ProjectDto> getProjectById(String id) {
        projectValidator.validateId(id);
        return projectRepository.findById(id)
                .map(projectConverter::buildProjectDtoFromProject);
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectConverter::buildProjectDtoFromProject)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProject(String id) {
        projectValidator.validateId(id);
        projectRepository.deleteById(id);
    }

    @Override
    public TaskDto addTaskToProject(String projectId, TaskDto taskDto, List<MultipartFile> files) throws IOException {
        projectValidator.validateId(projectId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));

        TaskBridge taskBridge = TaskBridgeProvider.getBridge(project.getMethodology(), null);
        Task task = taskBridge.createTask(taskDto, taskBridge.getInitialStatus(), project.getMethodology());
        Task savedTask = taskService.saveTask(task, files);

        project.addTaskId(savedTask.getTaskId());
        projectRepository.save(project);
        return taskConverter.buildTaskDtoFromTask(savedTask);
    }

    @Override
    public void updateTaskStatus(String projectId, String taskId, String newStatus) {
        projectValidator.validateId(projectId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));

        Task task = taskService.getTaskById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));

        taskStatusHandler.handleTaskStatus(project, task, newStatus);
        taskService.saveTask(task);
        projectRepository.save(project);
    }

    private Project createProjectWithMethodology(ProjectFactory projectFactory, ProjectDto projectDto) {
        return switch (projectDto.getMethodology()) {
            case "Agile" ->
                    projectFactory.createProject(projectDto.getName(), projectDto.getDescription(), projectDto.getSprintDuration(), projectDto.getCurrentSprint());
            case "Kanban" ->
                    projectFactory.createProject(projectDto.getName(), projectDto.getDescription(), projectDto.getWorkInProgressLimit());
            case "RUP" ->
                    projectFactory.createProject(projectDto.getName(), projectDto.getDescription(), projectDto.getPhase());
            default -> throw new IllegalArgumentException("Invalid methodology: " + projectDto.getMethodology());
        };
    }

    private Project saveProject(Project project) {
        return projectRepository.save(project);
    }
}