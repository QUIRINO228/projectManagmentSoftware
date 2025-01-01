package org.example.projectmanagement.exceptions.teams;

public class TeamNameExistsException extends RuntimeException {
    public TeamNameExistsException(String message) {
        super(message);
    }
}