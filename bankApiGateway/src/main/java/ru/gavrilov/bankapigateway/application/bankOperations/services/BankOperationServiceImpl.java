package ru.gavrilov.bankapigateway.application.bankOperations.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gavrilov.bankapigateway.application.bankOperations.clients.BankOperationsClient;
import ru.gavrilov.bankapigateway.application.bankOperations.dto.BankOperationResponse;
import ru.gavrilov.bankapigateway.application.bankOperations.dto.properties.BankOperationType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankOperationServiceImpl implements BankOperationService {
    private final BankOperationsClient bankOperationsClient;

    @Override
    public ResponseEntity<BankOperationResponse> deposit(long accountId, double amount) {
        return bankOperationsClient.deposit(accountId, amount);
    }

    @Override
    public ResponseEntity<BankOperationResponse> withdraw(long accountId, double amount) {
        return bankOperationsClient.withdraw(accountId, amount);
    }

    @Override
    public ResponseEntity<BankOperationResponse> transfer(long fromAccountId, long toAccountId, double amount) {
        return bankOperationsClient.transfer(fromAccountId, toAccountId, amount);
    }

    @Override
    public ResponseEntity<List<BankOperationResponse>> getAllBankOperationsByTypeAndAccountId(long accountId, BankOperationType type) {
        return bankOperationsClient.getAllBankOperationsByTypeAndAccountId(accountId, type);
    }
}
