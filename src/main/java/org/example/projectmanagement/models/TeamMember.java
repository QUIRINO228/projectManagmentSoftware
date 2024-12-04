package org.example.projectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.projectmanagement.models.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "team_members")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamMember {
    @Id
    private String memberId;
    private String teamId;
    private String userId;
    private Role role;
}