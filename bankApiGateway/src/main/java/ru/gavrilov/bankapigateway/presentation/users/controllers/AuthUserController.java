package ru.gavrilov.bankapigateway.presentation.users.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilov.bankapigateway.application.users.contracts.AuthService;
import ru.gavrilov.bankapigateway.application.users.exceptions.UserCreateException;
import ru.gavrilov.bankapigateway.application.users.models.User;
import ru.gavrilov.bankapigateway.presentation.users.dto.login.LoginUserRequest;
import ru.gavrilov.bankapigateway.presentation.users.dto.login.LoginUserResponse;
import ru.gavrilov.bankapigateway.presentation.users.dto.login.mappers.LoginUserRequestMapper;
import ru.gavrilov.bankapigateway.presentation.users.dto.registration.RegistrateUserRequest;
import ru.gavrilov.bankapigateway.presentation.users.dto.registration.mappers.RegistrateUserRequestMapper;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthUserController {
    private final AuthService authService;
    private final LoginUserRequestMapper loginUserRequestMapper;
    private final RegistrateUserRequestMapper registrateUserRequestMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginRequest) {
        String token;

        try {
            token = authService.loginUser(loginUserRequestMapper.fromRequest(loginRequest));
        }
        catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        }

        return ResponseEntity.ok(new LoginUserResponse(token));
    }

    @PostMapping("/registration")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(@RequestBody RegistrateUserRequest registrationRequest) {
        User user;

        try {
            user = authService.createUser(registrateUserRequestMapper.fromRequest(registrationRequest));
        }
        catch (UserCreateException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Registration successful: %s", user));
    }
}
