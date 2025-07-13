package ru.gavrilov.exceptions.operations;

public class InvalidDepositAccountException extends InvalidDepositException {
    public InvalidDepositAccountException(String message) {
        super(message);
    }
}
