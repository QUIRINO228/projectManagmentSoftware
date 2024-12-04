package org.example.projectmanagement.services.teams;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.TeamDto;
import org.example.projectmanagement.models.Team;
import org.example.projectmanagement.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamValidator teamValidator;
    private final TeamConverter teamConverter;

    @Override
    public TeamDto createTeam(TeamDto teamDto) {
        if (!teamValidator.isValidTeam(null, teamDto)) {
            return null;
        }
        Team team = saveTeam(teamConverter.buildTeamFromDto(teamDto));
        return teamConverter.buildTeamDtoFromTeam(team);
    }

    @Override
    public TeamDto updateTeam(String id, TeamDto teamDto) {
        if (!teamValidator.isValidTeam(id, teamDto)) {
            return null;
        }
        Team team = teamConverter.buildTeamFromDto(teamDto);
        team.setTeamId(id);
        saveTeam(team);
        return teamConverter.buildTeamDtoFromTeam(team);
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

    private Team saveTeam(Team team) {
        return teamRepository.save(team);
    }
}