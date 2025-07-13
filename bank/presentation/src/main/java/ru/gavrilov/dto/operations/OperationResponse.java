package ru.gavrilov.dto.operations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gavrilov.dto.operations.properties.OperationDtoType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationResponse {
    private long id;
    private long accountId;
    private OperationDtoType operationType;
    private LocalDateTime dateTime;
}
