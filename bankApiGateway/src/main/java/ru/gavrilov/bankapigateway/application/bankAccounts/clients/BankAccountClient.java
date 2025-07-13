package ru.gavrilov.bankapigateway.application.bankAccounts.clients;

import org.springframework.http.ResponseEntity;
import ru.gavrilov.bankapigateway.application.bankAccounts.dto.BankAccountResponse;

import java.util.List;

public interface BankAccountClient {
    ResponseEntity<BankAccountResponse> getBankAccountById(long id);
    ResponseEntity<BankAccountResponse> createBankAccount(long userId);
    ResponseEntity<List<BankAccountResponse>> getAllBankAccounts();
    ResponseEntity<List<BankAccountResponse>> getAllBankAccountsByUserId(long userId);
    ResponseEntity<Double> getBankAccountBalance(long id);
}
