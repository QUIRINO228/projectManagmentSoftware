package org.example.projectmanagement.services.version;

import lombok.AllArgsConstructor;
import org.example.projectmanagement.dtos.FileMetadataDto;
import org.example.projectmanagement.dtos.VersionDto;
import org.example.projectmanagement.flyweight.VersionFlyweightFactory;
import org.example.projectmanagement.models.Project;
import org.example.projectmanagement.models.Version;
import org.example.projectmanagement.repositories.ProjectRepository;
import org.example.projectmanagement.services.file.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VersionServiceImpl implements VersionService {

    private final ProjectRepository projectRepository;
    private final FileService fileService;

    @Override
    public Optional<VersionDto> getVersionById(String projectId, String versionId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));

        return project.getVersions().stream()
                .filter(version -> version.getVersionId().equals(versionId))
                .findFirst()
                .map(VersionDto::new);
    }

    @Override
    public VersionDto addVersionToProject(String projectId, VersionDto versionDto, MultipartFile file) throws IOException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));

        FileMetadataDto fileMetadata = fileService.uploadFilesWithMetadata(List.of(file)).get(0);
        Version version = VersionFlyweightFactory.getVersion(
                versionDto.getVersionName(),
                versionDto.getReleaseDate(),
                fileMetadata.getFilePath()
        );

        project.addVersion(version);
        projectRepository.save(project);
        return new VersionDto(version);
    }
}