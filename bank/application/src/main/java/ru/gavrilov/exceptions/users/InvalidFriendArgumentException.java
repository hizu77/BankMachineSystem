package ru.gavrilov.exceptions.users;

public class InvalidFriendArgumentException extends UserException {
    public InvalidFriendArgumentException(String message) {
        super(message);
    }
}
