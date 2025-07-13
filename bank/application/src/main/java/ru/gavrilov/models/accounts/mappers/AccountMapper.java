package ru.gavrilov.models.accounts.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gavrilov.dao.operations.OperationDao;
import ru.gavrilov.dao.users.UserDao;
import ru.gavrilov.entities.accounts.AccountEntity;
import ru.gavrilov.entities.operations.OperationEntity;
import ru.gavrilov.models.accounts.Account;

import java.util.ArrayList;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final OperationDao operationDao;
    private final UserDao userDao;

    public Account toDomain(AccountEntity accountEntity) {
        var operations = accountEntity.getOperations().stream()
                .map(OperationEntity::getId)
                .toList();

        return Account.builder()
                .id(accountEntity.getId())
                .balance(accountEntity.getBalance())
                .ownerId(accountEntity.getOwner().getId())
                .operationsId(operations)
                .build();
    }

    public AccountEntity toEntity(Account account) {
        var operations = account.getOperationsId().stream()
                .map(id -> operationDao.findById(id.longValue()))
                .collect(Collectors.toCollection(ArrayList::new));

        var owner = userDao.findById(account.getOwnerId());

        return AccountEntity.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .operations(operations)
                .owner(owner)
                .build();
    }
}
