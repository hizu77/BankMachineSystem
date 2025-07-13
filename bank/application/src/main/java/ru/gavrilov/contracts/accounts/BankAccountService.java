package ru.gavrilov.contracts.accounts;

import ru.gavrilov.exceptions.operations.*;
import ru.gavrilov.models.accounts.Account;
import ru.gavrilov.models.operations.Operation;
import ru.gavrilov.exceptions.accounts.InvalidAccountArgumentsException;
import ru.gavrilov.exceptions.accounts.GetBalanceException;
import ru.gavrilov.models.operations.properties.OperationType;

import java.util.List;


public interface BankAccountService {
    Account createAccount(Account account) throws InvalidAccountArgumentsException;

    Account findAccountById(long accountId);

    List<Account> findAllAccountsByUserId(long userId);

    List<Operation> findAllOperationsByTypeAndAccountId(OperationType operationType, long accountId);

    List<Account> findAll();

    double getBalance(long accountId) throws GetBalanceException;

    Operation deposit(long accountId, double amount)
            throws InvalidDepositAccountException, InvalidDepositAmountException;

    Operation withdraw(long accountId, double amount)
            throws InvalidWithdrawAccountException, InvalidWithdrawAmountException;

    Operation transfer(long toId, long fromId, double amount)
            throws InvalidTransferAccountException, InvalidTransferAmountException;
}
