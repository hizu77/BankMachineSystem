package ru.gavrilov.models.operations.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gavrilov.dao.accounts.AccountDao;
import ru.gavrilov.entities.operations.OperationEntity;
import ru.gavrilov.models.operations.Operation;

@Component
@RequiredArgsConstructor
public class OperationMapper {
    private final OperationTypeMapper typeMapper;
    private final AccountDao accountDao;

    public Operation toDomain(OperationEntity operationEntity) {
        return Operation.builder()
                .id(operationEntity.getId())
                .accountId(operationEntity.getAccount().getId())
                .operationType(typeMapper.toDomainAttribute(operationEntity.getOperationType()))
                .dateTime(operationEntity.getDateTime())
                .build();
    }

    public OperationEntity toEntity(Operation operation) {
        return OperationEntity.builder()
                .id(operation.getId())
                .account(accountDao.findById(operation.getAccountId()))
                .operationType(typeMapper.toDbAttribute(operation.getOperationType()))
                .dateTime(operation.getDateTime())
                .build();
    }
}
