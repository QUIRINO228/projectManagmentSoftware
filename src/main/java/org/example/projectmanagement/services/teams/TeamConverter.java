package org.example.projectmanagement.services.teams;

import org.example.projectmanagement.dtos.TeamDto;
import org.example.projectmanagement.models.Team;
import org.springframework.stereotype.Component;

@Component
public interface TeamConverter {
    Team buildTeamFromDto(TeamDto teamDto);
    TeamDto buildTeamDtoFromTeam(Team team);
}
