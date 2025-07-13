package ru.gavrilov.models.operations.mappers;

import org.springframework.stereotype.Component;
import ru.gavrilov.entities.operations.properties.OperationTypeDb;
import ru.gavrilov.models.operations.properties.OperationType;

@Component
public class OperationTypeMapper {
    public OperationType toDomainAttribute(OperationTypeDb type) {
        return switch (type) {
            case CHECK_BALANCE -> OperationType.CHECK_BALANCE;
            case DEPOSIT -> OperationType.DEPOSIT;
            case WITHDRAW -> OperationType.WITHDRAW;
            case CREATE_ACCOUNT -> OperationType.CREATE_ACCOUNT;
        };
    }

    public OperationTypeDb toDbAttribute(OperationType type) {
        return switch (type) {
            case CHECK_BALANCE -> OperationTypeDb.CHECK_BALANCE;
            case WITHDRAW -> OperationTypeDb.WITHDRAW;
            case DEPOSIT -> OperationTypeDb.DEPOSIT;
            case CREATE_ACCOUNT -> OperationTypeDb.CREATE_ACCOUNT;
        };
    }
}
