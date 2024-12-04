package org.example.projectmanagement.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.TeamDto;
import org.example.projectmanagement.services.ServiceFacade;
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

    private final ServiceFacade serviceFacade;

    @PostMapping("/create")
    public ResponseEntity<Object> createTeam(@RequestBody TeamDto teamDto, @RequestHeader("Authorization") String token) {
        return handleRequest(() -> {
            TeamDto createdTeam = serviceFacade.createTeam(teamDto);
            return ResponseEntity.ok(Map.of("status", "success", "data", createdTeam));
        });
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getTeamById(@PathVariable String id, @RequestHeader("Authorization") String token) {
        return handleRequest(() -> {
            Optional<TeamDto> teamDto = serviceFacade.getTeamById(id, token);
            return teamDto.map(dto -> ResponseEntity.ok(Map.of("status", "success", "data", dto)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", "Team not found")));
        });
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllTeams(@RequestHeader("Authorization") String token) {
        return handleRequest(() -> {
            List<TeamDto> teams = serviceFacade.getAllTeams(token);
            return ResponseEntity.ok(Map.of("status", "success", "data", teams));
        });
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateTeam(@PathVariable String id, @RequestBody TeamDto teamDto, @RequestHeader("Authorization") String token) {
        log.info(teamDto.toString());
        return handleRequest(() -> {
            TeamDto updatedTeam = serviceFacade.updateTeam(id, teamDto, token);
            return ResponseEntity.ok(Map.of("status", "success", "data", updatedTeam));
        });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteTeam(@PathVariable String id, @RequestHeader("Authorization") String token) {
        return handleRequest(() -> {
            serviceFacade.deleteTeam(id, token);
            return ResponseEntity.noContent().build();
        });
    }

    @PostMapping("/add-to/{teamId}/members/{memberId}")
    public ResponseEntity<Object> addMemberToTeam(@PathVariable String teamId, @PathVariable String memberId, @RequestHeader("Authorization") String token) {
        return handleRequest(() -> {
            boolean added = serviceFacade.addMemberToTeam(teamId, memberId, token);
            return added ? ResponseEntity.ok(Map.of("status", "success", "message", "Member added successfully"))
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", "Team not found"));
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
}