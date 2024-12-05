package org.example.projectmanagement.controllers;

import lombok.AllArgsConstructor;
import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.dtos.TaskDto;
import org.example.projectmanagement.services.project.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;


    @PostMapping("/create")
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        ProjectDto createdProject = projectService.createProject(projectDto);
        return ResponseEntity.ok(createdProject);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable String id) {
        Optional<ProjectDto> projectDto = projectService.getProjectById(id);
        return projectDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable String id, @RequestBody ProjectDto projectDto) {
        ProjectDto updatedProject = projectService.updateProject(id, projectDto);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{projectId}/create-task", consumes = "multipart/form-data")
    public ResponseEntity<TaskDto> addTaskToProject(
            @PathVariable String projectId,
            @RequestPart("task") TaskDto taskDto,
            @RequestPart("file") List<MultipartFile> files) throws IOException {
        TaskDto createdTask = projectService.addTaskToProject(projectId, taskDto, files);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{projectId}/tasks/{taskId}/status")
    public void updateTaskStatus(
            @PathVariable String projectId,
            @PathVariable String taskId,
            @RequestParam String newStatus) {
        projectService.updateTaskStatus(projectId, taskId, newStatus);
    }
}