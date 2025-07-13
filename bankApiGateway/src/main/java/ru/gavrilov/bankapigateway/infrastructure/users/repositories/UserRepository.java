package ru.gavrilov.bankapigateway.infrastructure.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gavrilov.bankapigateway.infrastructure.users.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}
