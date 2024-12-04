package org.example.projectmanagement.services;

import lombok.AllArgsConstructor;
import org.example.projectmanagement.dtos.*;
import org.example.projectmanagement.services.project.ProjectService;
import org.example.projectmanagement.services.teams.TeamServiceProxy;
import org.example.projectmanagement.services.user.AuthService;
import org.example.projectmanagement.services.user.RegistrationService;
import org.example.projectmanagement.services.version.VersionService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class ServiceFacade {

    private final AuthService authService;
    private final RegistrationService registrationService;
    private final TeamServiceProxy teamServiceProxy;
    private final ProjectService projectService;
    private final VersionService versionService;

    // AuthService methods
    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        return authService.authenticate(authRequestDto);
    }

    public boolean createUser(UserDto userDto) {
        return registrationService.createUser(userDto);
    }

    // TeamServiceProxy methods
    public TeamDto createTeam(TeamDto teamDto) {
        return teamServiceProxy.createTeam(teamDto);
    }

    public Optional<TeamDto> getTeamById(String id, String token) throws AccessDeniedException {
        return teamServiceProxy.getTeamById(id, token);
    }

    public List<TeamDto> getAllTeams(String token) throws AccessDeniedException {
        return teamServiceProxy.getAllTeams(token);
    }

    public TeamDto updateTeam(String id, TeamDto teamDto, String token) throws AccessDeniedException {
        return teamServiceProxy.updateTeam(id, teamDto, token);
    }

    public void deleteTeam(String id, String token) throws AccessDeniedException {
        teamServiceProxy.deleteTeam(id, token);
    }

    public boolean addMemberToTeam(String teamId, String memberId, String token) throws AccessDeniedException {
        return teamServiceProxy.addMemberToTeam(teamId, memberId, token);
    }

    // ProjectService methods
    public ProjectDto createProject(ProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    public Optional<ProjectDto> getProjectById(String id) {
        return projectService.getProjectById(id);
    }

    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    public ProjectDto updateProject(String id, ProjectDto projectDto) {
        return projectService.updateProject(id, projectDto);
    }

    public void deleteProject(String id) {
        projectService.deleteProject(id);
    }

    public TaskDto addTaskToProject(String projectId, TaskDto taskDto, List<MultipartFile> files) throws IOException {
        return projectService.addTaskToProject(projectId, taskDto, files);
    }

    public void updateTaskStatus(String projectId, String taskId, String newStatus) {
        projectService.updateTaskStatus(projectId, taskId, newStatus);
    }

    // VersionService methods
    public VersionDto addVersionToProject(String projectId, VersionDto versionDto, MultipartFile file) throws IOException {
        return versionService.addVersionToProject(projectId, versionDto, file);
    }

    public Optional<VersionDto> getVersionById(String projectId, String versionId) {
        return versionService.getVersionById(projectId, versionId);
    }
}