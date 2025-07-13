package ru.gavrilov.bankapigateway.application.users.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gavrilov.bankapigateway.application.bankAccounts.dto.BankAccountResponse;
import ru.gavrilov.bankapigateway.application.bankAccounts.services.BankAccountService;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.BankUserResponse;
import ru.gavrilov.bankapigateway.application.bankUsers.services.BankUserService;
import ru.gavrilov.bankapigateway.application.users.contracts.ClientService;
import ru.gavrilov.bankapigateway.infrastructure.users.entities.UserEntity;
import ru.gavrilov.bankapigateway.infrastructure.users.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final UserRepository userRepository;
    private final BankUserService bankUserService;
    private final BankAccountService bankAccountService;

    @Override
    public ResponseEntity<BankUserResponse> getBankUserByClientLogin(String clientLogin) {
        Optional<UserEntity> user = userRepository.findByLogin(clientLogin);

        return bankUserService.getBankUserById(user.map(UserEntity::getBankUserId).orElseThrow());
    }

    @Override
    public ResponseEntity<List<BankAccountResponse>> getBankAccountsByClientLogin(String clientLogin) {
        Optional<UserEntity> user = userRepository.findByLogin(clientLogin);

        return bankAccountService.getAllBankAccountsByUserId(user.map(UserEntity::getBankUserId).orElseThrow());
    }

    @Override
    public ResponseEntity<BankUserResponse> addFriendToClient(String clientLogin, long friendId) {
        Optional<UserEntity> user = userRepository.findByLogin(clientLogin);

        return bankUserService.addUserToFriend(user.map(UserEntity::getBankUserId).orElseThrow(), friendId);
    }

    @Override
    public ResponseEntity<BankUserResponse> removeFriendToClient(String clientLogin, long friendId) {
        Optional<UserEntity> user = userRepository.findByLogin(clientLogin);

        return bankUserService.removeUserFriend(user.map(UserEntity::getBankUserId).orElseThrow(), friendId);
    }

    @Override
    public ResponseEntity<BankAccountResponse> createBankAccount(String clientLogin) {
        Optional<UserEntity> user = userRepository.findByLogin(clientLogin);

        return bankAccountService.createBankAccount(user.map(UserEntity::getBankUserId).orElseThrow());
    }
}
