package org.example.projectmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private String teamId;
    private Set<String> projectIds;
    private String name;
    private String description;
    private Set<String> memberIds;
}
