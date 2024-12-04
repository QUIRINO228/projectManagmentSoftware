package org.example.projectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Document(collection = "projects")
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    private String projectId;
    private String name;
    private String description;
    private Set<String> teamIds;
    private List<String> taskIds;
    private Set<Version> versions = new HashSet<>();
    private String methodology;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<Task> tasks = new HashSet<>();

    // Agile-specific attributes
    private Integer sprintDuration;
    private Integer currentSprint;

    // Kanban-specific attributes
    private Integer workInProgressLimit;

    // RUP-specific attributes
    private String phase;

    public void addTaskId(String taskId) {
        this.taskIds.add(taskId);
    }

    public void addVersion(Version version) {
        this.versions.add(version);
    }
}