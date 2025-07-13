package ru.gavrilov.bankapigateway.application.bankUsers.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gavrilov.bankapigateway.application.bankUsers.clients.BankUserClient;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.BankUserCreateRequest;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.BankUserResponse;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.properties.BankUserGenderType;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.properties.BankUserHairColorType;
import ru.gavrilov.bankapigateway.infrastructure.users.entities.UserEntity;
import ru.gavrilov.bankapigateway.infrastructure.users.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankUserServiceImpl implements BankUserService {
    private final BankUserClient bankUserClient;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<BankUserResponse> createBankUser(BankUserCreateRequest request) {
        Optional<UserEntity> user = userRepository.findByLogin(request.getLogin());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserEntity userEntity = user.get();
        if (userEntity.getBankUserId() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        ResponseEntity<BankUserResponse> created = bankUserClient.createBankUser(request);

        if (!created.getStatusCode().is2xxSuccessful()) {
            return created;
        }

        userEntity.setBankUserId(created.getBody().getId());
        userRepository.save(userEntity);

        return created;
    }

    @Override
    public ResponseEntity<BankUserResponse> getBankUserById(long id) {
        return bankUserClient.getBankUserById(id);
    }

    @Override
    public ResponseEntity<List<BankUserResponse>> getAllFriendsByUserId(long userId) {
        return bankUserClient.getAllFriendsByUserId(userId);
    }

    @Override
    public ResponseEntity<List<BankUserResponse>> getAllUsersByHairColorAndGender(BankUserHairColorType hairColor, BankUserGenderType gender) {
        return bankUserClient.getAllUsersByHairColorAndGender(hairColor, gender);
    }

    @Override
    public ResponseEntity<BankUserResponse> addUserToFriend(long userId, long friendId) {
        return bankUserClient.addUserToFriend(userId, friendId);
    }

    @Override
    public ResponseEntity<BankUserResponse> removeUserFriend(long userId, long friendId) {
        return bankUserClient.removeUserFriend(userId, friendId);
    }
}
