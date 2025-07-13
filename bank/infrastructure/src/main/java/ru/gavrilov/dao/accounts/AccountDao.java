package ru.gavrilov.dao.accounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilov.entities.accounts.AccountEntity;

import java.util.List;


@Repository
public interface AccountDao extends JpaRepository<AccountEntity, Long> {
    AccountEntity findById(long id);

    List<AccountEntity> findByOwnerId(long ownerId);
}