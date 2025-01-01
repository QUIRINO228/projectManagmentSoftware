package org.example.projectmanagement.services.version;

import org.example.projectmanagement.dtos.VersionDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface VersionService {
    Optional<VersionDto> getVersionById(String projectId, String versionId);
}