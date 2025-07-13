package ru.gavrilov.dto.operations.mappers;

import org.springframework.stereotype.Component;
import ru.gavrilov.dto.operations.properties.OperationDtoType;
import ru.gavrilov.models.operations.properties.OperationType;

@Component
public class OperationDtoTypeMapper {
    public OperationDtoType toDtoAttribute(OperationType operationDtoType) {
        return switch (operationDtoType) {
            case CHECK_BALANCE -> OperationDtoType.CHECK_BALANCE;
            case WITHDRAW -> OperationDtoType.WITHDRAW;
            case DEPOSIT -> OperationDtoType.DEPOSIT;
            case CREATE_ACCOUNT -> OperationDtoType.CREATE_ACCOUNT;
        };
    }

    public OperationType toDomainAttribute(OperationDtoType operationDtoType) {
        return switch (operationDtoType) {
            case CHECK_BALANCE -> OperationType.CHECK_BALANCE;
            case WITHDRAW -> OperationType.WITHDRAW;
            case DEPOSIT -> OperationType.DEPOSIT;
            case CREATE_ACCOUNT -> OperationType.CREATE_ACCOUNT;
        };
    }
}
