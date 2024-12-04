package org.example.projectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@Document(collection = "versions")
@AllArgsConstructor
@NoArgsConstructor
public class Version {
    @Id
    private String versionId;
    private String versionName;
    private LocalDate releaseDate;
    private String executableFilePath;
}