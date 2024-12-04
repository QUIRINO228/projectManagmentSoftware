package org.example.projectmanagement.services.teams;

import org.example.projectmanagement.dtos.TeamDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TeamService {
    TeamDto createTeam(TeamDto teamDto);
    Optional<TeamDto> getTeamById(String id);
    List<TeamDto> getAllTeams();
    TeamDto updateTeam(String id, TeamDto teamDto);
    void deleteTeam(String id);
    boolean addMember(String teamId, String memberId);
    boolean isUserInTeam(String teamId, String userId);
}