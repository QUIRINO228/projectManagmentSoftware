package org.example.projectmanagement.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.AuthRequestDto;
import org.example.projectmanagement.dtos.AuthResponseDto;
import org.example.projectmanagement.dtos.UserDto;
import org.example.projectmanagement.exceptions.users.EmailAlreadyInUseException;
import org.example.projectmanagement.services.user.AuthService;
import org.example.projectmanagement.services.user.RegistrationService;
import org.example.projectmanagement.services.user.UserService;
import org.example.projectmanagement.utils.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final AuthService authService;
    private final RegistrationService registrationService;
    private final UserService userService;

    @GetMapping("/is-expired")
    public ResponseEntity<Object> isExpired(@RequestHeader("Authorization") String authorizationHeader) {
        String token = TokenUtils.extractToken(authorizationHeader);
        try {
            return userService.isExpired(token)
                    ? ResponseEntity.ok().body(Map.of("message", "Token is expired"))
                    : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Token is not expired"));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Token has expired"));
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponse = authService.authenticate(authRequestDto);
        return authResponse != null
                ? ResponseEntity.ok().body(Map.of("message", "Authentication successful", "accessToken", authResponse.getAccessToken(), "refreshToken", authResponse.getRefreshToken()))
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Authentication failed"));
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        try {
            registrationService.createUser(userDto);
            return ResponseEntity.ok().body(Map.of("message", "Registration successful"));
        } catch (EmailAlreadyInUseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getUserDtoByToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = TokenUtils.extractToken(authorizationHeader);
        UserDto userDto = userService.getUserDtoByToken(token);
        return userDto != null
                ? ResponseEntity.ok().body(userDto)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
    }

    @GetMapping("/get-role")
    public ResponseEntity<Object> getUserRoleByToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = TokenUtils.extractToken(authorizationHeader);
        UserDto userDto = userService.getUserDtoByToken(token);
        return userDto != null
                ? ResponseEntity.ok().body(userDto.getRole())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") String id) {
        UserDto userDto = userService.getUserById(id);
        return userDto != null
                ? ResponseEntity.ok().body(userDto)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}