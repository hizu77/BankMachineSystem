package ru.gavrilov.storage.application.services.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.gavrilov.storage.application.mappers.users.UserEventMapper;
import ru.gavrilov.storage.application.models.users.UserChangeEvent;
import ru.gavrilov.storage.infrastructure.entities.users.UserChangeEventEntity;
import ru.gavrilov.storage.infrastructure.repositories.users.UserEventRepository;

@Service
@RequiredArgsConstructor
public class UserEventConsumer {
    private final UserEventRepository userEventRepository;
    private final ObjectMapper objectMapper;
    private final UserEventMapper userEventMapper;
    private final static String CLIENT_TOPIC = "client-topic";

    @KafkaListener(topics = CLIENT_TOPIC, groupId = "storage")
    public void listenUserEvents(String message) {
        try {
            UserChangeEvent event = objectMapper.readValue(message, UserChangeEvent.class);
            UserChangeEventEntity entity = userEventMapper.toEntity(event);
            userEventRepository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
