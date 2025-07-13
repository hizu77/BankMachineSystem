package ru.gavrilov.bankapigateway.application.bankUsers.services;

import org.springframework.http.ResponseEntity;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.BankUserCreateRequest;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.BankUserResponse;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.properties.BankUserGenderType;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.properties.BankUserHairColorType;

import java.util.List;

public interface BankUserService {
    ResponseEntity<BankUserResponse> createBankUser(BankUserCreateRequest request);
    ResponseEntity<BankUserResponse> getBankUserById(long id);
    ResponseEntity<List<BankUserResponse>> getAllFriendsByUserId(long userId);
    ResponseEntity<List<BankUserResponse>> getAllUsersByHairColorAndGender(
            BankUserHairColorType hairColor, BankUserGenderType gender);
    ResponseEntity<BankUserResponse> addUserToFriend(long userId, long friendId);
    ResponseEntity<BankUserResponse> removeUserFriend(long userId, long friendId);
}
