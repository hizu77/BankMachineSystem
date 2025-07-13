package ru.gavrilov.exceptions.operations;

public class InvalidTransferAccountException extends InvalidTransferException {
    public InvalidTransferAccountException(String message) {
        super(message);
    }
}
