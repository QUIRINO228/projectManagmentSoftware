package org.example.projectmanagement.services.teams;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.dtos.TeamDto;
import org.example.projectmanagement.exceptions.teams.InvalidTeamDataException;
import org.example.projectmanagement.models.Team;
import org.example.projectmanagement.models.TeamMember;
import org.example.projectmanagement.repositories.TeamMemberRepository;
import org.example.projectmanagement.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamValidator teamValidator;
    private final TeamConverter teamConverter;

    @Override
    public TeamDto createTeam(TeamDto teamDto) {
        try {
            if (!teamValidator.isValidTeam(null, teamDto)) {
                throw new InvalidTeamDataException("Invalid team data for creation: " + teamDto);
            }
            Team team = saveTeam(teamDto);
            log.info("Team created: {}", teamDto);
            return teamConverter.buildTeamDtoFromTeam(team);
        } catch (Exception e) {
            throw new RuntimeException("Error creating team: " + teamDto, e);
        }
    }

    @Override
    public TeamDto updateTeam(String id, TeamDto teamDto) {
        try {
            if (!teamValidator.isValidTeam(id, teamDto)) {
                log.warn("Invalid team data for update: {}", teamDto);
                return null;
            }

            Team existingTeam = teamRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Team not found: " + id));
            existingTeam.setTeamId(id);
            existingTeam.setName(teamDto.getName());
            existingTeam.setDescription(teamDto.getDescription());
            existingTeam.setMemberIds(existingTeam.getMemberIds());
            existingTeam.setProjectIds(existingTeam.getProjectIds());
            Team updatedTeam = teamRepository.save(existingTeam);

            return teamConverter.buildTeamDtoFromTeam(updatedTeam);
        } catch (Exception e) {
            log.error("Error updating team with id {}: {}", id, teamDto, e);
            return null;
        }
    }

    @Override
    public Optional<TeamDto> getTeamById(String id) {
        return teamRepository.findById(id)
                .map(teamConverter::buildTeamDtoFromTeam);
    }

    @Override
    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(teamConverter::buildTeamDtoFromTeam)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTeam(String id) {
        teamRepository.deleteById(id);
    }

    @Override
    public boolean addMember(String teamId, String memberId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (optionalTeam.isPresent()) {
            Team team = optionalTeam.get();
            team.getMemberIds().add(memberId);
            teamRepository.save(team);
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserInTeam(String teamId, String userId) {
        Optional<TeamDto> teamDto = teamRepository.findById(teamId)
                .map(teamConverter::buildTeamDtoFromTeam);
        return teamDto.map(dto -> dto.getMemberIds().contains(userId)).orElse(false);
    }

    @Override
    public List<TeamDto> getAllUsersTeams(String userId) {
        return teamRepository.findAll().stream()
                .filter(team -> Optional.ofNullable(team.getMemberIds()).orElse(Collections.emptySet()).contains(userId))
                .map(teamConverter::buildTeamDtoFromTeam)
                .collect(Collectors.toList());
    }

    @Override
    public List<Team> getTeamsByUserId(String userId) {
        List<String> teamIds = teamMemberRepository.findAllByUserId(userId).stream()
                .map(TeamMember::getTeamId)
                .collect(Collectors.toList());
        return teamRepository.findAllById(teamIds);
    }

    @Override
    public Team saveTeam(TeamDto teamDto) {
        return teamRepository.save(teamConverter.buildTeamFromDto(teamDto));
    }
}