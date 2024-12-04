package org.example.projectmanagement.services.user;

import org.example.projectmanagement.dtos.AuthRequestDto;
import org.example.projectmanagement.dtos.AuthResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthResponseDto authenticate(AuthRequestDto authRequestDto);
}