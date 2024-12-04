package org.example.projectmanagement.services.teams;

import org.example.projectmanagement.dtos.TeamDto;
import org.springframework.stereotype.Service;

@Service
public interface TeamValidator {
    boolean isValidTeam(String id, TeamDto teamDto);
}
