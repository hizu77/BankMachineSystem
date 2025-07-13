package ru.gavrilov.storage.application.models.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gavrilov.storage.infrastructure.entities.users.UserEventType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChangeEvent {
    private long userId;
    private UserEventType eventType;
    private String changes;
    private LocalDateTime date;
}
