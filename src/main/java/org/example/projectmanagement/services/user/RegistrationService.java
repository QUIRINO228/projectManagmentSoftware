package org.example.projectmanagement.services.user;

import org.example.projectmanagement.dtos.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {
    boolean createUser(UserDto userDTO);
    boolean isEmailAlreadyInUse(String email);
}