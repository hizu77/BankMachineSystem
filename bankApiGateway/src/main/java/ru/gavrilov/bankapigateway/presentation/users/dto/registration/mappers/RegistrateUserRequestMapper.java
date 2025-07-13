package ru.gavrilov.bankapigateway.presentation.users.dto.registration.mappers;

import org.springframework.stereotype.Component;
import ru.gavrilov.bankapigateway.application.users.models.User;
import ru.gavrilov.bankapigateway.presentation.users.dto.registration.RegistrateUserRequest;

@Component
public class RegistrateUserRequestMapper {
    public User fromRequest(RegistrateUserRequest request) {
        return User.builder()
                .login(request.getUsername())
                .password(request.getPassword())
                .userRole(request.getUserRole())
                .build();
    }
}
