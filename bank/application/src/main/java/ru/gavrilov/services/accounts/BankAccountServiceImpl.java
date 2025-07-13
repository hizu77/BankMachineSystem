package ru.gavrilov.services.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilov.contracts.accounts.BankAccountService;
import ru.gavrilov.dao.accounts.AccountDao;
import ru.gavrilov.dao.operations.OperationDao;
import ru.gavrilov.dao.users.UserDao;
import ru.gavrilov.entities.accounts.AccountEntity;
import ru.gavrilov.entities.operations.OperationEntity;
import ru.gavrilov.entities.operations.properties.OperationTypeDb;
import ru.gavrilov.entities.users.UserEntity;
import ru.gavrilov.exceptions.accounts.GetBalanceException;
import ru.gavrilov.exceptions.accounts.InvalidAccountArgumentsException;
import ru.gavrilov.exceptions.operations.*;
import ru.gavrilov.models.accounts.Account;
import ru.gavrilov.models.accounts.mappers.AccountMapper;
import ru.gavrilov.models.operations.Operation;
import ru.gavrilov.models.operations.mappers.OperationMapper;
import ru.gavrilov.models.operations.mappers.OperationTypeMapper;
import ru.gavrilov.models.operations.properties.OperationType;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final AccountDao accountDao;
    private final UserDao userDao;
    private final OperationDao operationDao;
    private final OperationMapper operationMapper;
    private final AccountMapper accountMapper;
    private final OperationTypeMapper operationTypeMapper;

    @Transactional
    @Override
    public Account createAccount(Account account) throws InvalidAccountArgumentsException {
        var user = userDao.findById(account.getOwnerId());
        if (user == null) {
            throw new InvalidAccountArgumentsException("Owner not found");
        }

        var accountEntity = accountDao.save(accountMapper.toEntity(account));

        var createAccountOperation = OperationEntity.builder()
                .account(accountEntity)
                .operationType(OperationTypeDb.CREATE_ACCOUNT)
                .dateTime(LocalDateTime.now())
                .build();

        var operation = operationDao.save(createAccountOperation);
        accountEntity.getOperations().add(operation);
        return accountMapper.toDomain(accountDao.save(accountEntity));
    }

    @Override
    public Account findAccountById(long accountId) {
        var accountEntity = accountDao.findById(accountId);

        if (accountEntity == null) {
            return null;
        }

        return accountMapper.toDomain(accountEntity);
    }

    @Override
    public List<Account> findAllAccountsByUserId(long userId) {
        return accountDao.findByOwnerId(userId)
                .stream().map(accountMapper::toDomain).toList();
    }

    @Override
    public List<Operation> findAllOperationsByTypeAndAccountId(OperationType operationType, long accountId) {
        return operationDao.findByOperationTypeAndAccountId(
                operationTypeMapper.toDbAttribute(operationType), accountId
        ).stream().map(operationMapper::toDomain).toList();
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll().stream().map(accountMapper::toDomain).toList();
    }

    @Transactional
    @Override
    public double getBalance(long accountId) throws GetBalanceException {
        var account = accountDao.findById(accountId);
        if (account == null) {
            throw new GetBalanceException("Account not found");
        }

        var checkBalanceOperation = OperationEntity.builder()
                .account(account)
                .operationType(OperationTypeDb.CHECK_BALANCE)
                .dateTime(LocalDateTime.now())
                .build();

        operationDao.save(checkBalanceOperation);
        return account.getBalance();
    }

    @Transactional
    @Override
    public Operation deposit(long accountId, double amount)
            throws InvalidDepositAccountException, InvalidDepositAmountException {
        var account = accountDao.findById(accountId);

        if (account == null) {
            throw new InvalidDepositAccountException("Account not found");
        }

        if (amount <= 0) {
            throw new InvalidDepositAmountException("Amount must be greater than 0");
        }

        double newBalance = account.getBalance() + amount;
        return operationMapper.toDomain(
                saveAccountBalance(account, newBalance, OperationTypeDb.DEPOSIT));
    }

    @Transactional
    @Override
    public Operation withdraw(long accountId, double amount)
            throws InvalidWithdrawAccountException, InvalidWithdrawAmountException {
        var account = accountDao.findById(accountId);

        if (account == null) {
            throw new InvalidWithdrawAccountException("Account not found");
        }

        if (amount <= 0 || amount > account.getBalance()) {
            throw new InvalidWithdrawAmountException("Amount must be greater than 0 and less than balance");
        }

        double newBalance = account.getBalance() - amount;
        return operationMapper.toDomain(
                saveAccountBalance(account, newBalance, OperationTypeDb.WITHDRAW));
    }

    @Transactional
    @Override
    public Operation transfer(long fromId, long toId, double amount)
            throws InvalidTransferAccountException, InvalidTransferAmountException {
        var fromAccount = accountDao.findById(fromId);
        var toAccount = accountDao.findById(toId);

        if (fromAccount == null || toAccount == null) {
            throw new InvalidTransferAccountException("Accounts not found");
        }

        var fromUser = userDao.findById(fromAccount.getOwner().getId());
        var toUser = userDao.findById(toAccount.getOwner().getId());
        double commission = calculateTransferCommission(fromUser, toUser, amount);

        if (amount <= 0 || amount + commission > fromAccount.getBalance()) {
            throw new InvalidTransferAmountException("Amount must be greater than 0 and less than balance");
        }

        double fromNewBalance = fromAccount.getBalance() - amount - commission;
        double  toNewBalance = toAccount.getBalance() + amount;

        saveAccountBalance(toAccount, toNewBalance, OperationTypeDb.DEPOSIT);
        return operationMapper.toDomain(
                saveAccountBalance(fromAccount, fromNewBalance, OperationTypeDb.WITHDRAW));
    }

    @Transactional
    protected OperationEntity saveAccountBalance(
            AccountEntity account,
            double newBalance,
            OperationTypeDb operationTypeDb) {
        var operation = OperationEntity.builder()
                .account(account)
                .operationType(operationTypeDb)
                .dateTime(LocalDateTime.now())
                .build();

        account.getOperations().add(operation);
        account.setBalance(newBalance);

        accountDao.save(account);
        return operationDao.save(operation);
    }

    private double calculateTransferCommission(
            UserEntity from, UserEntity to, double amount
    ) {
        if (from.getId() == to.getId()) {
            return 0;
        }

        if (from.getFriends().stream().anyMatch(f -> f.getId() == to.getId())) {
            return amount * 0.03;
        }

        return amount * 0.1;
    }
}