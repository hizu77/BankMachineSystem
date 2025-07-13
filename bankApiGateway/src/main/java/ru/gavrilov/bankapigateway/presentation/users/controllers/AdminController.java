package ru.gavrilov.bankapigateway.presentation.users.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gavrilov.bankapigateway.application.bankAccounts.services.BankAccountService;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.BankUserCreateRequest;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.properties.BankUserGenderType;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.properties.BankUserHairColorType;
import ru.gavrilov.bankapigateway.application.bankUsers.services.BankUserService;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final BankUserService bankUserService;
    private final BankAccountService bankAccountService;

    @PostMapping("/users")
    public ResponseEntity<?> createBankUser(@RequestBody BankUserCreateRequest request) {
       return bankUserService.createBankUser(request);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllBankUsersByGenderAndHairColor(
            @RequestParam("hair-color") BankUserHairColorType hairColor,
            @RequestParam("gender") BankUserGenderType gender) {
        return bankUserService.getAllUsersByHairColorAndGender(hairColor, gender);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getBankUserById(@PathVariable("id") long id) {
        return bankUserService.getBankUserById(id);
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllBankUserAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<?> getAllBankUserAccountsByUserId(@PathVariable("userId") long userId) {
        return bankAccountService.getAllBankAccountsByUserId(userId);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getBankUserAccountByIdWithOperations(@PathVariable("id") long id) {
        return bankAccountService.getBankAccountById(id);
    }
}
