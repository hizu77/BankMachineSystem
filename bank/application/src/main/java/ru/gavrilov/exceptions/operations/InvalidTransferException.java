package ru.gavrilov.exceptions.operations;

public class InvalidTransferException extends OperationException {
    public InvalidTransferException(String message) {
        super(message);
    }
}
