package ru.gavrilov.exceptions.accounts;

import ru.gavrilov.exceptions.operations.OperationException;

public class GetBalanceException extends OperationException {
    public GetBalanceException(String message) {
        super(message);
    }
}
