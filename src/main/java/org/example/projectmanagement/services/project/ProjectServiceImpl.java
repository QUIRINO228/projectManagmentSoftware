package org.example.projectmanagement.services.project;

import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.bridge.TaskBridge;
import org.example.projectmanagement.bridge.TaskBridgeProvider;
import org.example.projectmanagement.dtos.*;
import org.example.projectmanagement.factory.ProjectFactory;
import org.example.projectmanagement.factory.ProjectFactoryProvider;
import org.example.projectmanagement.flyweight.VersionFlyweightFactory;
import org.example.projectmanagement.handlers.TaskStatusHandler;
import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.models.Task;
import org.example.projectmanagement.models.Version;
import org.example.projectmanagement.repositories.ProjectRepository;
import org.example.projectmanagement.services.file.FileService;
import org.example.projectmanagement.services.task.TaskConverter;
import org.example.projectmanagement.services.task.TaskService;
import org.example.projectmanagement.services.teams.TeamService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
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
    private final TeamService teamService;
    private final FileService fileService;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ProjectValidator projectValidator,
                              ProjectConverter projectConverter,
                              TaskConverter taskConverter,
                              TaskService taskService,
                              @Qualifier("agileTaskStatusHandler") TaskStatusHandler taskStatusHandler, TeamService teamService, FileService fileService) {
        this.projectRepository = projectRepository;
        this.projectValidator = projectValidator;
        this.projectConverter = projectConverter;
        this.taskConverter = taskConverter;
        this.taskService = taskService;
        this.taskStatusHandler = taskStatusHandler;
        this.teamService = teamService;
        this.fileService = fileService;
    }

    @Override
    public VersionDto addVersionToProject(String projectId, MultipartFile file) throws IOException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));

        FileMetadataDto fileMetadata = fileService.uploadFilesWithMetadata(List.of(file)).get(0);
        Version version = VersionFlyweightFactory.getVersion(
                fileMetadata.getFilePath()
        );
        version.setReleaseDate(LocalDate.now());
        project.addVersion(version);
        projectRepository.save(project);
        return VersionDto.fromVersion(version);
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        projectValidator.validateProjectDto(projectDto);
        ProjectFactory projectFactory = ProjectFactoryProvider.getFactory(projectDto.getMethodology());
        Project project = createProjectWithMethodology(projectFactory, projectDto);
        project = projectRepository.save(project);

        for (String teamId : projectDto.getTeamIds()) {
            TeamDto team = teamService.getTeamById(teamId)
                    .orElseThrow(() -> new IllegalArgumentException("Team not found: " + teamId));
            if (team.getProjectIds() == null) {
                team.setProjectIds(new HashSet<>());
            }
            team.getProjectIds().add(project.getProjectId());
            teamService.saveTeam(team);
        }

        return projectConverter.buildProjectDtoFromProject(project);
    }

    @Override
    public ProjectDto updateProject(String id, ProjectDto projectDto) {
        projectValidator.validateId(id);
        projectValidator.validateProjectDto(projectDto);
        ProjectFactory projectFactory = ProjectFactoryProvider.getFactory(projectDto.getMethodology());
        Project project = createProjectWithMethodology(projectFactory, projectDto);
        project.setProjectId(id);
        projectRepository.save(project);
        return projectDto;
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
    public TaskDto createTask(String projectId, TaskDto taskDto) {
        projectValidator.validateId(projectId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));

        TaskBridge taskBridge = TaskBridgeProvider.getBridge(project.getMethodology());
        Task task = taskBridge.createTask(taskDto, taskBridge.getInitialStatus(), project.getMethodology());
        Task savedTask = taskService.saveTask(task);

        project.addTaskId(savedTask.getTaskId());
        projectRepository.save(project);
        return taskConverter.buildTaskDtoFromTask(savedTask);
    }

    @Override
    public List<ProjectDto> getAllUsersProjects(String userId) {
        List<TeamDto> teams = teamService.getAllUsersTeams(userId);
        log.info("Teams: {}", teams);
        Set<String> projectIds = teams.stream()
                .filter(team -> team.getProjectIds() != null)
                .flatMap(team -> team.getProjectIds().stream())
                .collect(Collectors.toSet());
        List<Project> userProjects = projectRepository.findAllById(projectIds);
        return userProjects.stream()
                .map(projectConverter::buildProjectDtoFromProject)
                .collect(Collectors.toList());
    }

    private Project createProjectWithMethodology(ProjectFactory projectFactory, ProjectDto projectDto) {
        return switch (projectDto.getMethodology()) {
            case "Agile" ->
                    projectFactory.createProject(projectDto, projectDto.getCurrentSprint());
            case "Kanban" ->
                    projectFactory.createProject(projectDto, projectDto.getWorkInProgressLimit());
            case "RUP" ->
                    projectFactory.createProject(projectDto, projectDto.getPhase());
            default -> throw new IllegalArgumentException("Invalid methodology: " + projectDto.getMethodology());
        };
    }
    @Override
    public void updateTaskStatus(String taskId, String newStatus) {
        TaskDto taskDto = taskService.getTaskById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));
        Project project = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + taskDto.getProjectId()));
        Task task = taskConverter.buildTaskFromDto(taskDto);
        task.setStatus(newStatus);
        taskStatusHandler.handleTaskStatus(project, task, newStatus);
        taskService.saveTask(task);
        projectRepository.save(project);
    }
}