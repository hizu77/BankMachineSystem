package ru.gavrilov.bankapigateway.application.users.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gavrilov.bankapigateway.application.users.contracts.AuthService;
import ru.gavrilov.bankapigateway.application.users.mappers.UserMapper;
import ru.gavrilov.bankapigateway.application.users.models.User;
import ru.gavrilov.bankapigateway.infrastructure.users.repositories.UserRepository;
import ru.gavrilov.bankapigateway.infrastructure.users.entities.UserEntity;
import ru.gavrilov.bankapigateway.application.users.exceptions.UserCreateException;
import ru.gavrilov.bankapigateway.utils.jwt.JwtTokenUtils;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) throws UserCreateException {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new UserCreateException(
                    String.format("User with login '%s' already exists", user.getLogin()
                    ));
        }

        UserEntity userEntity = userMapper.toEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.fromEntity(userRepository.save(userEntity));
    }

    @Override
    public String loginUser(User user) throws BadCredentialsException {
        authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getLogin(),
                            user.getPassword()
                    )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());

        return jwtTokenUtils.generateToken(userDetails);
    }
}
