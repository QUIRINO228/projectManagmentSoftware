package org.example.projectmanagement.controllers;

import lombok.AllArgsConstructor;
import org.example.projectmanagement.dtos.VersionDto;
import org.example.projectmanagement.services.version.VersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/versions")
@AllArgsConstructor
public class VersionController {

    private final VersionService versionService;



    @GetMapping("/{projectId}/{versionId}")
    public ResponseEntity<VersionDto> getVersionById(
            @PathVariable("projectId") String projectId,
            @PathVariable("versionId") String versionId) {
        Optional<VersionDto> versionDto = versionService.getVersionById(projectId, versionId);
        return versionDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}