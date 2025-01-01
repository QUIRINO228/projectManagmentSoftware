package org.example.projectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;

@Data
@Document(collection = "teams")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    private String teamId;
    private Set<String> projectIds;
    private String name;
    private String description;
    private Set<String> memberIds;
}