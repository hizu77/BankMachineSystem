package ru.gavrilov.bankapigateway.application.users.exceptions;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
