package ru.gavrilov.bankapigateway.presentation.users.dto.login;

import lombok.Data;

@Data
public class LoginUserRequest {
    private final String username;
    private final String password;
}
