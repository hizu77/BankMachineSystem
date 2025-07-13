package ru.gavrilov.storage.application.services.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.gavrilov.storage.application.mappers.accounts.AccountEventMapper;
import ru.gavrilov.storage.application.models.accounts.AccountChangeEvent;
import ru.gavrilov.storage.infrastructure.entities.accounts.AccountChangeEventEntity;
import ru.gavrilov.storage.infrastructure.repositories.accounts.AccountEventRepository;

@Service
@RequiredArgsConstructor
public class AccountEventConsumer {
    private final AccountEventRepository accountEventRepository;
    private final ObjectMapper objectMapper;
    private final AccountEventMapper accountEventMapper;
    private final static String ACCOUNT_TOPIC = "account-topic";

    @KafkaListener(topics = ACCOUNT_TOPIC, groupId = "storage")
    public void listenAccountEvents(String message) {
        try {
            AccountChangeEvent event = objectMapper.readValue(message, AccountChangeEvent.class);
            AccountChangeEventEntity entity = accountEventMapper.toEntity(event);
            accountEventRepository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
