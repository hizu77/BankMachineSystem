package ru.gavrilov.exceptions.operations;

public class InvalidWithdrawException extends OperationException {
    public InvalidWithdrawException(String message) {
        super(message);
    }
}
