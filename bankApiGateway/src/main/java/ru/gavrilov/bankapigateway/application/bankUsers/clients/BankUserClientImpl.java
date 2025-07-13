package ru.gavrilov.bankapigateway.application.bankUsers.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.BankUserCreateRequest;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.BankUserResponse;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.properties.BankUserGenderType;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.properties.BankUserHairColorType;
import ru.gavrilov.bankapigateway.application.clientsProxy.ProxyRestService;

import java.util.List;


@Component
@RequiredArgsConstructor
public class BankUserClientImpl implements BankUserClient {
    private final RestClient restClient;
    private final ProxyRestService proxyRestService;


    @Override
    public ResponseEntity<BankUserResponse> createBankUser(BankUserCreateRequest request) {
        return proxyRestService.proxyRequest(
                () -> restClient.post()
                .uri(builder -> builder
                        .path("/users")
                        .build())
                .body(request)
                .retrieve(), BankUserResponse.class);
    }

    @Override
    public ResponseEntity<BankUserResponse> getBankUserById(long id) {
         return proxyRestService.proxyRequest(
                () -> restClient.get()
                        .uri(builder -> builder
                                .path("/users/{id}")
                                .build(id))
                        .retrieve(), BankUserResponse.class
        );
    }

    @Override
    public ResponseEntity<List<BankUserResponse>> getAllFriendsByUserId(long userId) {
        return proxyRestService.proxyRequest(
                () -> restClient.get()
                .uri(builder -> builder
                        .path("/users/{usersId}/friends")
                        .build(userId))
                .retrieve(), new ParameterizedTypeReference<List<BankUserResponse>>() {}
        );
    }

    @Override
    public ResponseEntity<List<BankUserResponse>> getAllUsersByHairColorAndGender(BankUserHairColorType hairColor, BankUserGenderType gender) {
        return proxyRestService.proxyRequest(
                () -> restClient.get()
                .uri(builder -> builder
                        .path("/users")
                        .queryParam("hair-color", hairColor)
                        .queryParam("gender", gender)
                        .build())
                .retrieve(), new ParameterizedTypeReference<List<BankUserResponse>>() {}
        );
    }

    @Override
    public ResponseEntity<BankUserResponse> addUserToFriend(long userId, long friendId) {
        return proxyRestService.proxyRequest(
                () -> restClient.post()
                .uri(builder -> builder
                        .path("/users/{userId}/friends")
                        .queryParam("friend-id", friendId)
                        .build(userId))
                .retrieve(), BankUserResponse.class
        );
    }

    @Override
    public ResponseEntity<BankUserResponse> removeUserFriend(long userId, long friendId) {
        return proxyRestService.proxyRequest(
                () -> restClient.delete()
                .uri(builder -> builder
                        .path("/users/{userId}/friends")
                        .queryParam("friend-id", friendId)
                        .build(userId))
                .retrieve(), BankUserResponse.class
        );
    }
}
