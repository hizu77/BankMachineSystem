package ru.gavrilov.bankapigateway.application.users.contracts;

import org.springframework.http.ResponseEntity;
import ru.gavrilov.bankapigateway.application.bankAccounts.dto.BankAccountResponse;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.BankUserResponse;

import java.util.List;


public interface ClientService {
    ResponseEntity<BankUserResponse> getBankUserByClientLogin(String clientLogin);
    ResponseEntity<List<BankAccountResponse>> getBankAccountsByClientLogin(String clientLogin);
    ResponseEntity<BankUserResponse> addFriendToClient(String clientLogin, long friendId);
    ResponseEntity<BankUserResponse> removeFriendToClient(String clientLogin, long friendId);
    ResponseEntity<BankAccountResponse> createBankAccount(String clientLogin);
}
