package org.example.projectmanagement.services.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.AuthRequestDto;
import org.example.projectmanagement.dtos.AuthResponseDto;
import org.example.projectmanagement.models.User;
import org.example.projectmanagement.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Override
    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        log.info("Authenticating user: {}", authRequestDto.getEmail());
        Optional<User> userOptional = userRepository.findByEmail(authRequestDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (BCrypt.checkpw(authRequestDto.getPassword(), user.getPassword())) {
                log.info("User authenticated successfully: {}", authRequestDto.getEmail());
                return generateJwtToken(user);
            }
        }
        log.warn("Authentication failed for user: {}", authRequestDto.getEmail());
        return null;
    }

    private AuthResponseDto generateJwtToken(User registeredUser) {
        String role = (registeredUser.getRole() != null) ? registeredUser.getRole().toString() : "USER";
        String accessToken = jwtUtil.generate(registeredUser.getId(), role, "ACCESS");
        String refreshToken = jwtUtil.generate(registeredUser.getId(), role, "REFRESH");
        return new AuthResponseDto(accessToken, refreshToken);
    }
}