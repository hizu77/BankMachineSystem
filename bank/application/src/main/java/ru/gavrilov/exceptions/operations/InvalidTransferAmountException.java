package ru.gavrilov.exceptions.operations;

public class InvalidTransferAmountException extends InvalidTransferException {
    public InvalidTransferAmountException(String message) {
        super(message);
    }
}
