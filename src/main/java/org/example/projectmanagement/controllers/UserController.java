package org.example.projectmanagement.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.AuthRequestDto;
import org.example.projectmanagement.dtos.AuthResponseDto;
import org.example.projectmanagement.dtos.UserDto;
import org.example.projectmanagement.services.user.AuthService;
import org.example.projectmanagement.services.user.RegistrationService;
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

    @GetMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponse = authService.authenticate(authRequestDto);
        return authResponse != null
                ? ResponseEntity.ok().body(Map.of("message", "Authentication successful", "accessToken", authResponse.getAccessToken(), "refreshToken", authResponse.getRefreshToken()))
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Authentication failed"));
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        return registrationService.createUser(userDto)
                ? ResponseEntity.ok().body(Map.of("message", "Registration successful"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Registration failed"));
    }
}