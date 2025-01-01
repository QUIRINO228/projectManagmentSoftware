package org.example.projectmanagement.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.dtos.TeamDto;
import org.example.projectmanagement.dtos.UserDto;
import org.example.projectmanagement.exceptions.teams.InvalidTeamDataException;
import org.example.projectmanagement.exceptions.teams.TeamNameExistsException;
import org.example.projectmanagement.exceptions.teams.TeamNotFoundException;
import org.example.projectmanagement.services.teams.TeamProxy;
import org.example.projectmanagement.services.teams.TeamService;
import org.example.projectmanagement.services.user.UserService;
import org.example.projectmanagement.utils.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
@AllArgsConstructor
@Slf4j
public class TeamController {

    private final TeamProxy teamProxy;
    private final UserService userService;
    private final TeamService teamService;

    @GetMapping("/get-teams")
    public ResponseEntity<Object> getTeamsByUserToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String userId = userService.getUserDtoByToken(token).getId();
        List<TeamDto> teamDtos = teamService.getAllUsersTeams(userId);
        return userId != null
                ? ResponseEntity.ok().body(teamDtos)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTeam(@RequestBody TeamDto teamDto, @RequestHeader("Authorization") String token) {
        return handleRequest(() -> {
            TeamDto createdTeam = teamProxy.createTeam(teamDto);
            return ResponseEntity.ok(createdTeam);
        });
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getTeamById(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        return handleRequest(() -> {
            Optional<TeamDto> teamDto = teamProxy.getTeamById(id, token);
            return teamDto.map(dto -> ResponseEntity.ok(Map.of("status", "success", "data", dto)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", "Team not found")));
        });
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllTeams(@RequestHeader("Authorization") String authorizationHeader) {
        String token = TokenUtils.extractToken(authorizationHeader);
        return handleRequest(() -> {
            List<TeamDto> teams = teamProxy.getAllTeams(token);
            return ResponseEntity.ok(Map.of("status", "success", "data", teams));
        });
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateTeam(@PathVariable("id") String id, @RequestBody TeamDto teamDto, @RequestHeader("Authorization") String authorizationHeader) {
        String token = TokenUtils.extractToken(authorizationHeader);
        log.info(teamDto.toString());
        return handleRequest(() -> {
            TeamDto updatedTeam = teamProxy.updateTeam(id, teamDto, token);
            return ResponseEntity.ok(Map.of("status", "success", "data", updatedTeam));
        });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteTeam(@PathVariable("id") String id, @RequestHeader("Authorization") String authorizationHeader) {
        String token = TokenUtils.extractToken(authorizationHeader);
        return handleRequest(() -> {
            teamProxy.deleteTeam(id, token);
            return ResponseEntity.noContent().build();
        });
    }

    @PostMapping("/add-to/{teamId}/members/{memberId}")
    public ResponseEntity<Object> addMemberToTeam(@PathVariable("teamId") String teamId, @PathVariable("memberId") String memberId, @RequestHeader("Authorization") String authorizationHeader) {
        String token = TokenUtils.extractToken(authorizationHeader);
        return handleRequest(() -> {
            boolean added = teamProxy.addMemberToTeam(teamId, memberId, token);
            return added ? ResponseEntity.ok(Map.of("status", "success", "message", "Member added successfully"))
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status", "error", "message", "Team not found"));
        });
    }

    private <T> ResponseEntity<T> handleRequest(RequestHandler<T> handler) {
        try {
            return handler.handle();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body((T) Map.of("status", "error", "message", "Access denied"));
        }
    }

    @FunctionalInterface
    private interface RequestHandler<T> {
        ResponseEntity<T> handle() throws AccessDeniedException;
    }

    @ExceptionHandler(InvalidTeamDataException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidTeamDataException(InvalidTeamDataException e) {
        return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTeamNotFoundException(TeamNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", e.getMessage()));
    }

    @ExceptionHandler(TeamNameExistsException.class)
    public ResponseEntity<Map<String, Object>> handleTeamNameExistsException(TeamNameExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("status", "error", "message", e.getMessage()));
    }
}