package org.example.projectmanagement.services.user;

import org.example.projectmanagement.dtos.ProjectDto;
import org.example.projectmanagement.dtos.UserDto;
import org.example.projectmanagement.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto getUserById(String id);

    List<User> getAllUsers();

    boolean userExists(String userId);

    User getUserByToken(String token);

    UserDto getUserDtoByToken(String token);

    boolean isExpired(String token);
}
