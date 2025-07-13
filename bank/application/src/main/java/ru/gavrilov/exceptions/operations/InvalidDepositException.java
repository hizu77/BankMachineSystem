package ru.gavrilov.exceptions.operations;

public class InvalidDepositException extends OperationException {
    public InvalidDepositException(String message) {
        super(message);
    }
}
