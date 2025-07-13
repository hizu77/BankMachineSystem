package ru.gavrilov.exceptions.accounts;

public class InvalidAccountArgumentsException extends AccountException{
    public InvalidAccountArgumentsException(String message) {
        super(message);
    }
}
