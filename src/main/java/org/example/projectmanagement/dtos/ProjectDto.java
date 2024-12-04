package org.example.projectmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.projectmanagement.models.Version;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDto {
    private String projectId;
    private String name;
    private String description;
    private Set<String> teamIds;
    private List<String> taskIds;
    private Set<Version> versions;
    private String methodology;
    private LocalDate startDate;
    private LocalDate endDate;

    // Agile-specific attributes
    private Integer sprintDuration;
    private Integer currentSprint;

    // Kanban-specific attributes
    private Integer workInProgressLimit;

    // RUP-specific attributes
    private String phase;
}