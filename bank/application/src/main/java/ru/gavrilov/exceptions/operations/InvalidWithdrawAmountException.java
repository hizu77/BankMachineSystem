package ru.gavrilov.exceptions.operations;

public class InvalidWithdrawAmountException extends InvalidWithdrawException {
    public InvalidWithdrawAmountException(String message) {
        super(message);
    }
}
