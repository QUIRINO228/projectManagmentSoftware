package org.example.projectmanagement.services.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.UserDto;
import org.example.projectmanagement.models.User;
import org.example.projectmanagement.models.enums.UserRole;
import org.example.projectmanagement.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;

    @Override
    public boolean createUser(UserDto userDTO) {
        String email = userDTO.getEmail();
        if (isEmailAlreadyInUse(email)) {
            log.warn("Email already in use: {}", email);
            return false;
        }
        User user = buildUserFromDto(userDTO);
        userRepository.save(user);
        log.info("Saving new user with : {}", email);
        return true;
    }

    @Override
    public boolean isEmailAlreadyInUse(String email) {
        boolean emailExists = userRepository.findByEmail(email).isPresent();
        if (emailExists) {
            log.warn("Email already in use: {}", email);
        }
        return emailExists;
    }

    private User buildUserFromDto(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()))
                .role(UserRole.USER)
                .active(true)
                .build();
    }
}