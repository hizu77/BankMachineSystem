package ru.gavrilov.dto.operations.mappers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gavrilov.dto.operations.OperationResponse;
import ru.gavrilov.models.operations.Operation;

@Component
@RequiredArgsConstructor
public class OperationResponseMapper {
    private final OperationDtoTypeMapper operationDtoTypeMapper;

    public OperationResponse toResponse(Operation operation) {
        return OperationResponse.builder()
                .id(operation.getId())
                .accountId(operation.getAccountId())
                .operationType(operationDtoTypeMapper.toDtoAttribute(operation.getOperationType()))
                .dateTime(operation.getDateTime())
                .build();
    }
}
