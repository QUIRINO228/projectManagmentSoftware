package org.example.projectmanagement.services.project;

import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.dtos.UserDto;
import org.example.projectmanagement.dtos.VersionDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface ProjectService {
    ProjectDto createProject(ProjectDto projectDto);
    ProjectDto updateProject(String id, ProjectDto projectDto);
    Optional<ProjectDto> getProjectById(String id);
    List<ProjectDto> getAllProjects();
    void deleteProject(String id);
    TaskDto createTask(String projectId, TaskDto taskDto);
    List<ProjectDto> getAllUsersProjects(String userId);

    VersionDto addVersionToProject(String projectId, MultipartFile file) throws IOException;

    void updateTaskStatus(String taskId, String newStatus);
}
