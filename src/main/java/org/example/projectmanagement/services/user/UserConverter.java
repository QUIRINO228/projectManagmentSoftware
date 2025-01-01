package org.example.projectmanagement.services.user;

import org.example.projectmanagement.dtos.UserDto;
import org.example.projectmanagement.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public static User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }
}
