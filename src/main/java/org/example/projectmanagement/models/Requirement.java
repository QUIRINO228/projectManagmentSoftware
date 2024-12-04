package org.example.projectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.projectmanagement.models.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "requirements")
@AllArgsConstructor
@NoArgsConstructor
public class Requirement  {
    @Id
    private String requirementId;
    private String projectId;
    private String description;
    private Status status;
}