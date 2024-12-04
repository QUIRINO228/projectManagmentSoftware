package org.example.projectmanagement.dtos;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AuthRequestDto {
    private String email;
    private String password;
}
