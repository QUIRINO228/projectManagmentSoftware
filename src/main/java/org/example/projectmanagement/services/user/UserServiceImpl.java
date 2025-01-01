package org.example.projectmanagement.services.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.dtos.UserDto;
import org.example.projectmanagement.models.User;
import org.example.projectmanagement.repositories.UserRepository;
import org.example.projectmanagement.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserConverter.toDto(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean userExists(String userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User getUserByToken(String token) {
        String userId = jwtUtil.getUserIdFromToken(token);
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDto getUserDtoByToken(String token) {
        User user = getUserByToken(token);
        return UserConverter.toDto(user);
    }

    @Override
    public boolean isExpired(String token) {
        return jwtUtil.isExpired(token);
    }

}
