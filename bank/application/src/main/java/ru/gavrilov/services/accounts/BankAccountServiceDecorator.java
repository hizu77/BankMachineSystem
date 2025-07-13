package ru.gavrilov.services.accounts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.gavrilov.contracts.accounts.BankAccountService;
import ru.gavrilov.exceptions.accounts.GetBalanceException;
import ru.gavrilov.exceptions.accounts.InvalidAccountArgumentsException;
import ru.gavrilov.exceptions.operations.*;
import ru.gavrilov.models.accounts.Account;
import ru.gavrilov.models.accounts.events.AccountChangeEvent;
import ru.gavrilov.models.accounts.events.AccountEventType;
import ru.gavrilov.models.operations.Operation;
import ru.gavrilov.models.operations.properties.OperationType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary
@RequiredArgsConstructor
public class BankAccountServiceDecorator implements BankAccountService {
    private final BankAccountService bankAccountService;
    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final static String BANK_ACCOUNT_TOPIC = "account-topic";

    @Override
    @Transactional
    public Account createAccount(Account account) throws InvalidAccountArgumentsException {
        Account createdAccount = bankAccountService.createAccount(account);
        Map<String, Object> changes = objectMapper.convertValue(createdAccount, Map.class);
        sendUserEvent(createdAccount.getId(), AccountEventType.CREATED, changes);
        return createdAccount;
    }

    @Override
    public Account findAccountById(long accountId) {
        return bankAccountService.findAccountById(accountId);
    }

    @Override
    public List<Account> findAllAccountsByUserId(long userId) {
        return bankAccountService.findAllAccountsByUserId(userId);
    }

    @Override
    public List<Operation> findAllOperationsByTypeAndAccountId(OperationType operationType, long accountId) {
        return bankAccountService.findAllOperationsByTypeAndAccountId(operationType, accountId);
    }

    @Override
    public List<Account> findAll() {
        return bankAccountService.findAll();
    }

    @Override
    public double getBalance(long accountId) throws GetBalanceException {
        return bankAccountService.getBalance(accountId);
    }

    @Override
    @Transactional
    public Operation deposit(long accountId, double amount) throws InvalidDepositAccountException, InvalidDepositAmountException {
        Operation operation = bankAccountService.deposit(accountId, amount);
        Map<String, Object> changes = new HashMap<String, Object>() {{
            put("deposit", amount);
        }};
        sendUserEvent(accountId, AccountEventType.DEPOSIT, changes);
        return operation;
    }

    @Override
    @Transactional
    public Operation withdraw(long accountId, double amount) throws InvalidWithdrawAccountException, InvalidWithdrawAmountException {
        Operation operation = bankAccountService.withdraw(accountId, amount);
        Map<String, Object> changes = new HashMap<String, Object>() {{
            put("withdraw", amount);
        }};
        sendUserEvent(accountId, AccountEventType.WITHDRAW, changes);
        return operation;
    }

    @Override
    @Transactional
    public Operation transfer(long toId, long fromId, double amount) throws InvalidTransferAccountException, InvalidTransferAmountException {
        Operation operation = bankAccountService.transfer(toId, fromId, amount);
        Map<String, Object> changes = new HashMap<String, Object>() {{
            put("transfer", amount);
        }};
        sendUserEvent(toId, AccountEventType.TRANSFER, changes);
        return operation;
    }

    private void sendUserEvent(long accountId, AccountEventType eventType, Map<String, Object> changes) {
        try {
            String changesJson = objectMapper.writeValueAsString(changes);

            AccountChangeEvent event = AccountChangeEvent.builder()
                    .accountId(accountId)
                    .eventType(eventType)
                    .changes(changesJson)
                    .timestamp(LocalDateTime.now())
                    .build();

            String json = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(BANK_ACCOUNT_TOPIC, accountId, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
