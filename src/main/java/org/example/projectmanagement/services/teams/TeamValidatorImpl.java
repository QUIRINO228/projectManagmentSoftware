package org.example.projectmanagement.services.teams;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.TeamDto;
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
                log.warn("Team ID not found: {}", id);
                return false;
            }
        }
        if (!areUsersValid(teamDto.getMemberIds().stream().toList())) {
            log.warn("Invalid users in team creation/update");
            return false;
        }
        if (teamRepository.existsByName(teamDto.getName())) {
            log.warn("Team name already exists: {}", teamDto.getName());
            return false;
        }
        return true;
    }

    private boolean areUsersValid(List<String> userIds) {
        return userIds.stream().allMatch(userService::userExists);
    }
}