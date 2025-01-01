package org.example.projectmanagement.utils;

public class TokenUtils {
    public static String extractToken(String authorizationHeader) {
        return authorizationHeader.replace("Bearer ", "");
    }
}