package org.example.projectmanagement.services.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.factory.ProjectFactory;
import org.example.projectmanagement.factory.ProjectFactoryProvider;
import org.example.projectmanagement.bridge.TaskBridge;
import org.example.projectmanagement.bridge.TaskBridgeProvider;
import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.models.Task;
import org.example.projectmanagement.repositories.ProjectRepository;
import org.example.projectmanagement.services.task.TaskConverter;
import org.example.projectmanagement.services.task.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectValidator projectValidator;
    private final ProjectConverter projectConverter;
    private final TaskConverter taskConverter;
    private final TaskService taskService;

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

        TaskBridge taskFactory = TaskBridgeProvider.getFactory(project.getMethodology());
        Task task = taskFactory.createTask(taskDto);
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

        task.setStatus(newStatus);
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