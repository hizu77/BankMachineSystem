package ru.gavrilov.bankapigateway.application.bankAccounts.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.gavrilov.bankapigateway.application.bankAccounts.dto.BankAccountResponse;
import ru.gavrilov.bankapigateway.application.clientsProxy.ProxyRestService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BankAccountClientImpl implements BankAccountClient {
    private final RestClient restClient;
    private final ProxyRestService proxyRestService;

    @Override
    public ResponseEntity<BankAccountResponse> getBankAccountById(long id) {
        return proxyRestService.proxyRequest(
                () -> restClient.get()
                .uri(builder -> builder
                        .path("/accounts/{id}")
                        .build(id))
                .retrieve(), BankAccountResponse.class
        );
    }

    @Override
    public ResponseEntity<BankAccountResponse> createBankAccount(long userId) {
        return proxyRestService.proxyRequest(
                () -> restClient.post()
                .uri(builder -> builder
                        .path("/users/{userId}/accounts")
                        .build(userId))
                .retrieve(), BankAccountResponse.class
        );
    }

    @Override
    public ResponseEntity<List<BankAccountResponse>> getAllBankAccounts() {
        return proxyRestService.proxyRequest(
                () -> restClient.get()
                .uri(builder -> builder
                        .path("/accounts")
                        .build())
                .retrieve(), new ParameterizedTypeReference<List<BankAccountResponse>>() {}
        );
    }

    @Override
    public ResponseEntity<List<BankAccountResponse>> getAllBankAccountsByUserId(long userId) {
        return proxyRestService.proxyRequest(
                () -> restClient.get()
                .uri(builder -> builder
                        .path("/users/{userId}/accounts")
                        .build(userId))
                .retrieve(), new ParameterizedTypeReference<List<BankAccountResponse>>() {}
        );
    }

    @Override
    public ResponseEntity<Double> getBankAccountBalance(long id) {
        return proxyRestService.proxyRequest(
                () -> restClient.get()
                .uri(builder -> builder
                        .path( "/accounts/{accountId}/balance")
                        .build(id))
                .retrieve(), Double.class
        );
    }
}
