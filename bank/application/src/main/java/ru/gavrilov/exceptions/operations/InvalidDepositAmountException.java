package ru.gavrilov.exceptions.operations;

public class InvalidDepositAmountException extends InvalidDepositException {
    public InvalidDepositAmountException(String message) {
        super(message);
    }
}
