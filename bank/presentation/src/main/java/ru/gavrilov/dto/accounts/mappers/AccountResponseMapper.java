package ru.gavrilov.dto.accounts.mappers;

import org.springframework.stereotype.Component;
import ru.gavrilov.dto.accounts.AccountResponse;
import ru.gavrilov.models.accounts.Account;

@Component
public class AccountResponseMapper {
    public AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .ownerId(account.getOwnerId())
                .balance(account.getBalance())
                .operationsId(account.getOperationsId())
                .build();
    }
}
