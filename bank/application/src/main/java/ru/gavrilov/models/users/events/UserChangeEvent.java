package ru.gavrilov.models.users.events;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserChangeEvent {
    private final long userId;
    private final UserEventType eventType;
    private final String changes;
    private final LocalDateTime date;
}
