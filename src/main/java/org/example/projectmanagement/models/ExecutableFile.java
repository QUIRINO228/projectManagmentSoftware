package org.example.projectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "versions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecutableFile {
    @Id
    private String id;
    private String versionId;
    private String name;
    private String path;
    private long size;
}
