package ru.gavrilov.bankapigateway.presentation.users.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gavrilov.bankapigateway.application.bankAccounts.services.BankAccountService;
import ru.gavrilov.bankapigateway.application.bankOperations.services.BankOperationService;
import ru.gavrilov.bankapigateway.application.users.contracts.ClientService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('CLIENT')")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final BankAccountService bankAccountService;
    private final BankOperationService bankOperationService;

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(Principal principal) {
        return clientService.getBankUserByClientLogin(principal.getName());
    }

    @GetMapping("/me/accounts")
    public ResponseEntity<?> getMyAccounts(Principal principal) {
        return clientService.getBankAccountsByClientLogin(principal.getName());
    }

    @GetMapping("/accounts/{id}/balance")
    @PreAuthorize("@securityService.hasBankAccountAccess(#id)")
    public ResponseEntity<?> getAccountBalance(@PathVariable("id") long id) {
        return bankAccountService.getBankAccountBalance(id);
    }

    @PostMapping("/me/friends")
    public ResponseEntity<?> addFriend(
            Principal principal,
            @RequestParam("friend-id") long friendId) {
        return clientService.addFriendToClient(principal.getName(), friendId);
    }

    @DeleteMapping("/me/friends")
    public ResponseEntity<?> deleteFriend(
            Principal principal,
            @RequestParam("friend-id") long friendId) {
        return clientService.removeFriendToClient(principal.getName(), friendId);
    }

    @PostMapping("/accounts/{id}/deposits")
    @PreAuthorize("@securityService.hasBankAccountAccess(#accountId)")
    public ResponseEntity<?> deposit(
            @PathVariable("id") long accountId,
            @RequestParam("amount") double amount
    ) {
        return bankOperationService.deposit(accountId, amount);
    }

    @PostMapping("/accounts/{id}/withdraws")
    @PreAuthorize("@securityService.hasBankAccountAccess(#accountId)")
    public ResponseEntity<?> withdraw(
            @PathVariable("id") long accountId,
            @RequestParam("amount") double amount
    ) {
        return bankOperationService.withdraw(accountId, amount);
    }

    @PostMapping("/accounts/{id}/transfers")
    @PreAuthorize("@securityService.hasBankAccountAccess(#accountId)")
    public ResponseEntity<?> transfer(
            @PathVariable("id") long accountId,
            @RequestParam("target-account-id") long targetAccountId,
            @RequestParam("amount") double amount
    ) {
        return bankOperationService.transfer(accountId, targetAccountId, amount);
    }

    @PostMapping("/me/accounts")
    public ResponseEntity<?> createAccount(Principal principal) {
        return clientService.createBankAccount(principal.getName());
    }
}
