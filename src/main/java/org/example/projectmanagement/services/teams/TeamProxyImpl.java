package org.example.projectmanagement.services.teams;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.TeamDto;
import org.example.projectmanagement.models.User;
import org.example.projectmanagement.services.user.UserService;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TeamProxyImpl implements TeamProxy {
    private final UserService userService;
    private final TeamService teamService;

    private void checkAccess(String teamId, String token) throws AccessDeniedException {
        User user = userService.getUserByToken(token);
        if (!teamService.isUserInTeam(teamId, user.getId())) {
            throw new AccessDeniedException("Access denied: User is not a member of the team");
        }
    }

    @Override
    public TeamDto createTeam(TeamDto teamDto) {
        return teamService.createTeam(teamDto);
    }

    @Override
    public Optional<TeamDto> getTeamById(String id, String token) throws AccessDeniedException {
        checkAccess(id, token);
        return teamService.getTeamById(id);
    }

    @Override
    public List<TeamDto> getAllTeams(String token) throws AccessDeniedException {
        return teamService.getAllTeams();
    }

    @Override
    public TeamDto updateTeam(String id, TeamDto teamDto, String token) throws AccessDeniedException {
        checkAccess(id, token);
        return teamService.updateTeam(id, teamDto);
    }

    @Override
    public void deleteTeam(String id, String token) throws AccessDeniedException {
        checkAccess(id, token);
        teamService.deleteTeam(id);
    }

    @Override
    public boolean addMemberToTeam(String teamId, String memberId, String token) throws AccessDeniedException {
        checkAccess(teamId, token);
        return teamService.addMember(teamId, memberId);
    }
}