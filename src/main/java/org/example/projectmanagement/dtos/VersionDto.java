package org.example.projectmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.projectmanagement.models.Version;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class VersionDto {
    private String versionId;
    private String versionName;
    private LocalDate releaseDate;
    private String executableFilePath;
    public static VersionDto fromVersion(Version version) {
        return VersionDto.builder()
                .versionId(version.getVersionId())
                .versionName(version.getVersionName())
                .releaseDate(version.getReleaseDate() != null ? LocalDate.parse(version.getReleaseDate().toString()) : null)
                .executableFilePath(version.getExecutableFilePath())
                .build();
    }
}