package ru.gavrilov.storage.application.mappers.users;

import org.springframework.stereotype.Component;
import ru.gavrilov.storage.application.models.users.UserChangeEvent;
import ru.gavrilov.storage.infrastructure.entities.users.UserChangeEventEntity;

@Component
public class UserEventMapper {
    public UserChangeEventEntity toEntity(UserChangeEvent userChangeEvent) {
        return UserChangeEventEntity.builder()
                .userId(userChangeEvent.getUserId())
                .eventType(userChangeEvent.getEventType())
                .changes(userChangeEvent.getChanges())
                .date(userChangeEvent.getDate())
                .build();
    }
}
