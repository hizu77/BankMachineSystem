package ru.gavrilov.storage.application.models.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gavrilov.storage.infrastructure.entities.accounts.AccountEventType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountChangeEvent {
    private long accountId;
    private AccountEventType eventType;
    private String changes;
    private LocalDateTime timestamp;
}
