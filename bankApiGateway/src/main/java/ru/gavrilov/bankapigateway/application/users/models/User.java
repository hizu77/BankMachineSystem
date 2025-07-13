package ru.gavrilov.bankapigateway.application.users.models;

import lombok.Builder;
import lombok.Data;
import ru.gavrilov.bankapigateway.infrastructure.users.entities.properties.UserRole;

@Data
@Builder
public class User {
    private final long id;
    private final String login;
    private final String password;
    private final UserRole userRole;
    private final Long bankUserId;
}
