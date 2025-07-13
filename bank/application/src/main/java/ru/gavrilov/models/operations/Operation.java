package ru.gavrilov.models.operations;

import lombok.*;
import ru.gavrilov.models.operations.properties.OperationType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Operation {
        private final long id;
        private final long accountId;
        private final OperationType operationType;
        private final LocalDateTime dateTime;
 }