package ru.gavrilov.bankapigateway.application.users.contracts;

import org.springframework.security.authentication.BadCredentialsException;
import ru.gavrilov.bankapigateway.application.users.exceptions.UserCreateException;
import ru.gavrilov.bankapigateway.application.users.models.User;


public interface AuthService {
    User createUser(User user) throws UserCreateException;
    String loginUser(User user) throws BadCredentialsException;
}
