package ru.gavrilov.storage.infrastructure.repositories.accounts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gavrilov.storage.infrastructure.entities.accounts.AccountChangeEventEntity;

public interface AccountEventRepository extends JpaRepository<AccountChangeEventEntity, Long> {}
