package ru.gavrilov.bankapigateway.presentation.users.dto.login;

import lombok.Data;

@Data
public class LoginUserResponse {
    private final String token;
}
