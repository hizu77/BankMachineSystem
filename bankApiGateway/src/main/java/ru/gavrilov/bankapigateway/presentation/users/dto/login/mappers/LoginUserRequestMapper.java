package ru.gavrilov.bankapigateway.presentation.users.dto.login.mappers;

import org.springframework.stereotype.Component;
import ru.gavrilov.bankapigateway.application.users.models.User;
import ru.gavrilov.bankapigateway.presentation.users.dto.login.LoginUserRequest;

@Component
public class LoginUserRequestMapper {
    public User fromRequest(LoginUserRequest loginUserRequest) {
        return User.builder()
                .login(loginUserRequest.getUsername())
                .password(loginUserRequest.getPassword())
                .build();
    }
}
