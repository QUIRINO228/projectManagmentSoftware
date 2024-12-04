package org.example.projectmanagement.services.user;

import org.example.projectmanagement.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getUserById(String id);

    List<User> getAllUsers();

    boolean userExists(String userId);

    User getUserByToken(String token);
}
