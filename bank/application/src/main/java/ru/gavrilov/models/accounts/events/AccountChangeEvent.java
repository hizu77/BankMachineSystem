package ru.gavrilov.models.accounts.events;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountChangeEvent {
    private final long accountId;
    private final AccountEventType eventType;
    private final String changes;
    private final LocalDateTime timestamp;
}
