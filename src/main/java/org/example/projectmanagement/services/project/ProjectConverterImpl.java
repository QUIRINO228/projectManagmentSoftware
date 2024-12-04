package org.example.projectmanagement.services.project;

import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.models.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverterImpl implements ProjectConverter {

    @Override
    public Project buildProjectFromDto(ProjectDto projectDto) {
        Project.ProjectBuilder builder = Project.builder()
                .projectId(projectDto.getProjectId())
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .teamIds(projectDto.getTeamIds())
                .taskIds(projectDto.getTaskIds())
                .versions(projectDto.getVersions())
                .methodology(projectDto.getMethodology())
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate());

        setMethodologySpecificAttributes(builder, projectDto);

        return builder.build();
    }

    @Override
    public ProjectDto buildProjectDtoFromProject(Project project) {
        ProjectDto.ProjectDtoBuilder builder = ProjectDto.builder()
                .projectId(project.getProjectId())
                .name(project.getName())
                .description(project.getDescription())
                .teamIds(project.getTeamIds())
                .taskIds(project.getTaskIds())
                .versions(project.getVersions())
                .methodology(project.getMethodology())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate());

        setMethodologySpecificAttributes(builder, project);

        return builder.build();
    }

    private <T> void setMethodologyAttributes(T builder, String methodology, Integer sprintDuration, Integer currentSprint, Integer workInProgressLimit, String phase) {
        switch (methodology) {
            case "Agile":
                if (builder instanceof Project.ProjectBuilder) {
                    ((Project.ProjectBuilder) builder).sprintDuration(sprintDuration).currentSprint(currentSprint);
                } else if (builder instanceof ProjectDto.ProjectDtoBuilder) {
                    ((ProjectDto.ProjectDtoBuilder) builder).sprintDuration(sprintDuration).currentSprint(currentSprint);
                }
                break;
            case "Kanban":
                if (builder instanceof Project.ProjectBuilder) {
                    ((Project.ProjectBuilder) builder).workInProgressLimit(workInProgressLimit);
                } else if (builder instanceof ProjectDto.ProjectDtoBuilder) {
                    ((ProjectDto.ProjectDtoBuilder) builder).workInProgressLimit(workInProgressLimit);
                }
                break;
            case "RUP":
                if (builder instanceof Project.ProjectBuilder) {
                    ((Project.ProjectBuilder) builder).phase(phase);
                } else if (builder instanceof ProjectDto.ProjectDtoBuilder) {
                    ((ProjectDto.ProjectDtoBuilder) builder).phase(phase);
                }
                break;
        }
    }

    private void setMethodologySpecificAttributes(Project.ProjectBuilder builder, ProjectDto projectDto) {
        setMethodologyAttributes(builder, projectDto.getMethodology(), projectDto.getSprintDuration(), projectDto.getCurrentSprint(), projectDto.getWorkInProgressLimit(), projectDto.getPhase());
    }

    private void setMethodologySpecificAttributes(ProjectDto.ProjectDtoBuilder builder, Project project) {
        setMethodologyAttributes(builder, project.getMethodology(), project.getSprintDuration(), project.getCurrentSprint(), project.getWorkInProgressLimit(), project.getPhase());
    }
}