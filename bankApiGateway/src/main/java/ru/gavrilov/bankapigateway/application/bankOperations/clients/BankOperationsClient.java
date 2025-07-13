package ru.gavrilov.bankapigateway.application.bankOperations.clients;

import org.springframework.http.ResponseEntity;
import ru.gavrilov.bankapigateway.application.bankOperations.dto.BankOperationResponse;
import ru.gavrilov.bankapigateway.application.bankOperations.dto.properties.BankOperationType;

import java.util.List;

public interface BankOperationsClient {
    ResponseEntity<BankOperationResponse> deposit(long accountId, double amount);
    ResponseEntity<BankOperationResponse> withdraw(long accountId, double amount);
    ResponseEntity<BankOperationResponse> transfer(long fromAccountId, long toAccountId, double amount);
    ResponseEntity<List<BankOperationResponse>> getAllBankOperationsByTypeAndAccountId(
            long accountId, BankOperationType type);
}
