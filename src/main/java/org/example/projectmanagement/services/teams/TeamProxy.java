package org.example.projectmanagement.services.teams;

import org.example.projectmanagement.dtos.TeamDto;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public interface TeamProxy {
    TeamDto createTeam(TeamDto teamDto);
    Optional<TeamDto> getTeamById(String id, String token) throws AccessDeniedException;
    void deleteTeam(String id, String token) throws AccessDeniedException;
    TeamDto updateTeam(String id, TeamDto teamDto, String token) throws AccessDeniedException;
    boolean addMemberToTeam(String teamId, String memberId, String token) throws AccessDeniedException;
    List<TeamDto> getAllTeams(String token) throws AccessDeniedException;
}
