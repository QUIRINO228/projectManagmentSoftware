package org.example.projectmanagement.services.version;

import lombok.AllArgsConstructor;
import org.example.projectmanagement.dtos.VersionDto;
import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VersionServiceImpl implements VersionService {

    private final ProjectRepository projectRepository;

    @Override
    public Optional<VersionDto> getVersionById(String projectId, String versionId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));

        return project.getVersions().stream()
                .filter(version -> version.getVersionId().equals(versionId))
                .findFirst()
                .map(VersionDto::fromVersion);
    }
}