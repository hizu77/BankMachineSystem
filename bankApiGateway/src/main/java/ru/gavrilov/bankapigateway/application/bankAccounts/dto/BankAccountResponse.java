package ru.gavrilov.bankapigateway.application.bankAccounts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class BankAccountResponse {
    private final long id;
    private final double balance;
    private final long ownerId;
    private final Collection<Long> operationsId;
}
