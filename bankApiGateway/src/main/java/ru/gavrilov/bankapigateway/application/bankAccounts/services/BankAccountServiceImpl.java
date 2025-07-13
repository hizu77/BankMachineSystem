package ru.gavrilov.bankapigateway.application.bankAccounts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gavrilov.bankapigateway.application.bankAccounts.clients.BankAccountClient;
import ru.gavrilov.bankapigateway.application.bankAccounts.dto.BankAccountResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountClient bankAccountClient;

    @Override
    public ResponseEntity<BankAccountResponse> getBankAccountById(long id) {
        return bankAccountClient.getBankAccountById(id);
    }

    @Override
    public ResponseEntity<BankAccountResponse> createBankAccount(long userId) {
        return bankAccountClient.createBankAccount(userId);
    }

    @Override
    public ResponseEntity<List<BankAccountResponse>> getAllBankAccounts() {
        return bankAccountClient.getAllBankAccounts();
    }

    @Override
    public ResponseEntity<List<BankAccountResponse>> getAllBankAccountsByUserId(long userId) {
        return bankAccountClient.getAllBankAccountsByUserId(userId);
    }

    @Override
    public ResponseEntity<Double> getBankAccountBalance(long id) {
        return bankAccountClient.getBankAccountBalance(id);
    }
}
