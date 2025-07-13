package ru.gavrilov.bankapigateway.application.users.mappers;

import org.springframework.stereotype.Component;
import ru.gavrilov.bankapigateway.application.users.models.User;
import ru.gavrilov.bankapigateway.infrastructure.users.entities.UserEntity;

@Component
public class UserMapper {
    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .userRole(user.getUserRole())
                .bankUserId(user.getBankUserId())
                .build();
    }

    public User fromEntity(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .login(userEntity.getLogin())
                .password(userEntity.getPassword())
                .userRole(userEntity.getUserRole())
                .bankUserId(userEntity.getBankUserId())
                .build();
    }
}
