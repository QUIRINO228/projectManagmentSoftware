package org.example.projectmanagement.services.teams;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.TeamDto;
import org.example.projectmanagement.exceptions.teams.InvalidTeamDataException;
import org.example.projectmanagement.exceptions.teams.TeamNameExistsException;
import org.example.projectmanagement.exceptions.teams.TeamNotFoundException;
import org.example.projectmanagement.models.Team;
import org.example.projectmanagement.repositories.TeamRepository;
import org.example.projectmanagement.services.user.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class TeamValidatorImpl implements TeamValidator {

    private final TeamRepository teamRepository;
    private final UserService userService;

    @Override
    public boolean isValidTeam(String id, TeamDto teamDto) {
        if (id != null) {
            Optional<Team> optionalTeam = teamRepository.findById(id);
            if (optionalTeam.isEmpty()) {
                throw new TeamNotFoundException("Team ID not found: " + id);
            }
        }
        if (teamDto.getMemberIds() != null && !areUsersValid(teamDto.getMemberIds().stream().toList())) {
            throw new InvalidTeamDataException("Invalid users in team creation/update");
        }
        if (teamRepository.existsByName(teamDto.getName())) {
            throw new TeamNameExistsException("Team name already exists: " + teamDto.getName());
        }
        return true;
    }

    private boolean areUsersValid(List<String> userIds) {
        return userIds.stream().allMatch(userService::userExists);
    }
}