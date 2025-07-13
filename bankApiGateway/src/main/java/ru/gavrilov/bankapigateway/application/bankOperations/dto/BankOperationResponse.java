package ru.gavrilov.bankapigateway.application.bankOperations.dto;

import lombok.Builder;
import lombok.Data;
import ru.gavrilov.bankapigateway.application.bankOperations.dto.properties.BankOperationType;

import java.time.LocalDateTime;

@Data
@Builder
public class BankOperationResponse {
    private final long id;
    private final long accountId;
    private final BankOperationType operationType;
    private final LocalDateTime dateTime;
}
