package ru.gavrilov.controllers.accounts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gavrilov.contracts.accounts.BankAccountService;
import ru.gavrilov.dto.accounts.AccountResponse;
import ru.gavrilov.dto.accounts.mappers.AccountResponseMapper;
import ru.gavrilov.dto.operations.OperationResponse;
import ru.gavrilov.dto.operations.mappers.OperationDtoTypeMapper;
import ru.gavrilov.dto.operations.mappers.OperationResponseMapper;
import ru.gavrilov.dto.operations.properties.OperationDtoType;
import ru.gavrilov.exceptions.operations.*;
import ru.gavrilov.models.accounts.Account;
import ru.gavrilov.services.accounts.BankAccountServiceImpl;
import ru.gavrilov.exceptions.accounts.GetBalanceException;
import ru.gavrilov.exceptions.accounts.InvalidAccountArgumentsException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountController {
    private final AccountResponseMapper accountResponseMapper;
    private final BankAccountService bankAccountService;
    private final OperationResponseMapper operationMapper;
    private final OperationDtoTypeMapper operationDtoTypeMapper;

    @Operation(
            summary = "Get account by id",
            tags = "Accounts",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account found"),
                    @ApiResponse(responseCode = "404", description = "Account not found")
            }
    )
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountResponse> getAccountById(
            @PathVariable("id") long id) {
        var account = bankAccountService.findAccountById(id);

        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(accountResponseMapper.toResponse(account));
    }

    @Operation(
            summary = "Create account",
            tags = "Accounts",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Incorrect arguments"),
                    @ApiResponse(responseCode = "201", description = "Account created")
            }
    )
    @PostMapping("/users/{userId}/accounts")
    public ResponseEntity<AccountResponse> createAccount(
            @PathVariable("userId") long userId) {
        Account account = Account.builder()
                .ownerId(userId)
                .operationsId(new ArrayList<>())
                .build();

        try {
            account = bankAccountService.createAccount(account);
        }
        catch (InvalidAccountArgumentsException exception) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(accountResponseMapper.toResponse(account));
    }

    @Operation(
            summary = "Get all accounts",
            tags = "Accounts",
            responses = {
                @ApiResponse(responseCode = "200", description = "Accounts list found")
            }
    )
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        var accounts = bankAccountService.findAll()
                .stream().map(accountResponseMapper::toResponse).toList();

        return ResponseEntity.ok(accounts);
    }

    @Operation(
            summary = "Get all accounts by owner user id",
            tags = "Accounts",
            responses = {
                @ApiResponse(responseCode = "200", description = "Accounts list found")
            }
    )
    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<List<AccountResponse>> getAllAccountsByUserId(
            @PathVariable("userId") long userId) {
        var accounts = bankAccountService.findAllAccountsByUserId(userId)
                .stream().map(accountResponseMapper::toResponse).toList();

        return ResponseEntity.ok(accounts);
    }

    @Operation(
            summary = "Get account balance",
            tags = "Accounts",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Balance found"),
                    @ApiResponse(responseCode = "400", description = "Account not found")
            }
    )
    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<Double> getBalance(
            @PathVariable("accountId") long accountId) {
        double balance;

        try {
            balance = bankAccountService.getBalance(accountId);
        }
        catch (GetBalanceException exception) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(balance);
    }

    @Operation(
            summary = "Bank account deposit",
            tags = "Operations",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful deposit"),
                    @ApiResponse(responseCode = "400", description = "Incorrect amount"),
                    @ApiResponse(responseCode = "404", description = "Account not found")
            }
    )
    @PostMapping("/accounts/{accountId}/deposits")
    public ResponseEntity<OperationResponse> deposit(
            @PathVariable("accountId") long accountId,
            @RequestParam("amount") double amount
    ) {
        ru.gavrilov.models.operations.Operation operation;

        try {
            operation = bankAccountService.deposit(accountId, amount);
        }
        catch (InvalidDepositAmountException exception) {
            return ResponseEntity.badRequest().build();
        }
        catch (InvalidDepositAccountException exception) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(operationMapper.toResponse(operation));
    }

    @Operation(
            summary = "Bank account withdraw",
            tags = "Operations",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful withdraw"),
                    @ApiResponse(responseCode = "400", description = "Incorrect amount"),
                    @ApiResponse(responseCode = "404", description = "Account not found")
            }
    )
    @PostMapping("/accounts/{accountId}/withdraws")
    public ResponseEntity<OperationResponse> withdraw(
            @PathVariable("accountId") long accountId,
            @RequestParam("amount") double amount
    ) {
        ru.gavrilov.models.operations.Operation operation;

        try {
            operation = bankAccountService.withdraw(accountId, amount);
        }
        catch (InvalidWithdrawAmountException exception) {
            return ResponseEntity.badRequest().build();
        }
        catch (InvalidWithdrawAccountException exception) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(operationMapper.toResponse(operation));
    }

    @Operation(
            summary = "Bank account transfer",
            tags = "Operations",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful transfer"),
                    @ApiResponse(responseCode = "400", description = "Incorrect amount"),
                    @ApiResponse(responseCode = "404", description = "Incorrect accounts id")
            }
    )
    @PostMapping("/accounts/{accountId}/transfers")
    public ResponseEntity<OperationResponse> transfer(
            @PathVariable("accountId") long accountId,
            @RequestParam("target-account-id") long targetAccountId,
            @RequestParam("amount") double amount
    ) {
        ru.gavrilov.models.operations.Operation operation;

        try {
            operation = bankAccountService.transfer(accountId, targetAccountId, amount);
        }
        catch (InvalidTransferAmountException exception) {
            return ResponseEntity.badRequest().build();
        }
        catch (InvalidTransferAccountException exception) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(operationMapper.toResponse(operation));
    }

    @Operation(
            summary = "Get all operations by type and accountId",
            tags = "Operations",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operations list found")
            }
    )
    @GetMapping("/accounts/{accountId}/operations")
    public ResponseEntity<List<OperationResponse>> getAllOperationsByTypeAndAccountId(
            @PathVariable("accountId") long accountId,
            @RequestParam("type") OperationDtoType type
    ) {
        var operations = bankAccountService.findAllOperationsByTypeAndAccountId(
                        operationDtoTypeMapper.toDomainAttribute(type), accountId)
                .stream().map(operationMapper::toResponse).toList();

        return ResponseEntity.ok(operations);
    }
}
