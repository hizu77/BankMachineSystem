package ru.gavrilov.storage.application.mappers.accounts;

import org.springframework.stereotype.Component;
import ru.gavrilov.storage.application.models.accounts.AccountChangeEvent;
import ru.gavrilov.storage.infrastructure.entities.accounts.AccountChangeEventEntity;

@Component
public class AccountEventMapper {
    public AccountChangeEventEntity toEntity(AccountChangeEvent accountChangeEvent) {
        return AccountChangeEventEntity.builder()
                .accountId(accountChangeEvent.getAccountId())
                .eventType(accountChangeEvent.getEventType())
                .changes(accountChangeEvent.getChanges())
                .date(accountChangeEvent.getTimestamp())
                .build();
    }
}
