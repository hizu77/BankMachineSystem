package ru.gavrilov.bankapigateway.presentation.users.dto.registration;

import lombok.Data;
import ru.gavrilov.bankapigateway.infrastructure.users.entities.properties.UserRole;

@Data
public class RegistrateUserRequest {
    private final String username;
    private final String password;
    private final UserRole userRole;

}
