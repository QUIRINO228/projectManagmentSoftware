package org.example.projectmanagement.services.teams;

import org.example.projectmanagement.dtos.TeamDto;
import org.example.projectmanagement.models.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamConverterImpl implements TeamConverter {

    @Override
    public Team buildTeamFromDto(TeamDto teamDto) {
        return Team.builder()
                .teamId(teamDto.getTeamId())
                .name(teamDto.getName())
                .description(teamDto.getDescription())
                .projectIds(teamDto.getProjectIds())
                .memberIds(teamDto.getMemberIds())
                .build();
    }

    @Override
    public TeamDto buildTeamDtoFromTeam(Team team) {
        return TeamDto.builder()
                .teamId(team.getTeamId())
                .name(team.getName())
                .projectIds(team.getProjectIds())
                .memberIds(team.getMemberIds())
                .description(team.getDescription())
                .build();
    }
}