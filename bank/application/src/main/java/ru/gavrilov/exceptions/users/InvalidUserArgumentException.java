package ru.gavrilov.exceptions.users;

public class InvalidUserArgumentException extends UserException {
    public InvalidUserArgumentException(String message) {
        super(message);
    }
}
