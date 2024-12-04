package org.example.projectmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.projectmanagement.models.Version;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionDto {
    private String versionId;
    private String versionName;
    private String releaseDate;
    private String executableFilePath;

    public VersionDto(Version version) {
        this.versionId = version.getVersionId();
        this.versionName = version.getVersionName();
        this.releaseDate = version.getReleaseDate().toString();
        this.executableFilePath = version.getExecutableFilePath();
    }
}