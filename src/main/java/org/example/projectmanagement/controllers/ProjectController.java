package org.example.projectmanagement.controllers;

import lombok.AllArgsConstructor;
import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.dtos.VersionDto;
import org.example.projectmanagement.services.project.ProjectService;
import org.example.projectmanagement.services.user.UserService;
import org.example.projectmanagement.utils.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @PostMapping("/{projectId}")
    public ResponseEntity<VersionDto> addVersionToProject(
            @PathVariable("projectId") String projectId,
            @RequestParam("file") MultipartFile file) throws IOException {
        VersionDto createdVersion = projectService.addVersionToProject(projectId, file);
        return ResponseEntity.ok(createdVersion);
    }

    @GetMapping("/get-projects")
    public ResponseEntity<Object> getProjectsByUserToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = TokenUtils.extractToken(authorizationHeader);
        String userId = userService.getUserDtoByToken(token).getId();
        List<ProjectDto> projectDto = projectService.getAllUsersProjects(userId);
        return userId != null
                ? ResponseEntity.ok().body(projectDto)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        ProjectDto createdProject = projectService.createProject(projectDto);
        return ResponseEntity.ok(createdProject);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable("id") String id) {
        Optional<ProjectDto> projectDto = projectService.getProjectById(id);
        return ResponseEntity.ok(projectDto.orElseThrow(() -> new RuntimeException("Project not found")));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable("id") String id, @RequestBody ProjectDto projectDto) {
        ProjectDto updatedProject = projectService.updateProject(id, projectDto);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projectId}/create-task")
    public ResponseEntity<TaskDto> createTask(
            @PathVariable("projectId") String projectId,
            @RequestBody TaskDto taskDto) {
        TaskDto createdTask = projectService.createTask(projectId, taskDto);
        return ResponseEntity.ok(createdTask);
    }
}