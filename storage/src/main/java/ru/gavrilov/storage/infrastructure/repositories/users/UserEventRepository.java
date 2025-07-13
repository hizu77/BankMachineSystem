package ru.gavrilov.storage.infrastructure.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gavrilov.storage.infrastructure.entities.users.UserChangeEventEntity;

public interface UserEventRepository extends JpaRepository<UserChangeEventEntity, Long> {}
