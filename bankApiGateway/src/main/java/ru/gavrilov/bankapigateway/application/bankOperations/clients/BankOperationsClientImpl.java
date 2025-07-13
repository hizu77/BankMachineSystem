package ru.gavrilov.bankapigateway.application.bankOperations.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.gavrilov.bankapigateway.application.bankAccounts.dto.BankAccountResponse;
import ru.gavrilov.bankapigateway.application.bankOperations.dto.BankOperationResponse;
import ru.gavrilov.bankapigateway.application.bankOperations.dto.properties.BankOperationType;
import ru.gavrilov.bankapigateway.application.clientsProxy.ProxyRestService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BankOperationsClientImpl implements BankOperationsClient {
    private final RestClient restClient;
    private final ProxyRestService proxyRestService;

    @Override
    public ResponseEntity<BankOperationResponse> deposit(long accountId, double amount) {
        return proxyRestService.proxyRequest(
                () -> restClient.post()
                .uri(builder -> builder
                        .path("/accounts/{accountId}/deposits")
                        .queryParam("amount", amount)
                        .build(accountId)
                )
                .retrieve(), BankOperationResponse.class
        );
    }

    @Override
    public ResponseEntity<BankOperationResponse> withdraw(long accountId, double amount) {
        return proxyRestService.proxyRequest(
                () -> restClient.post()
                .uri(builder -> builder
                        .path("/accounts/{accountId}/withdraws")
                        .queryParam("amount", amount)
                        .build(accountId)
                )
                .retrieve(), BankOperationResponse.class
        );
    }

    @Override
    public ResponseEntity<BankOperationResponse> transfer(long fromAccountId, long toAccountId, double amount) {
        return proxyRestService.proxyRequest(
                () -> restClient.post()
                .uri(builder -> builder
                        .path("/accounts/{accountId}/transfers")
                        .queryParam("target-account-id", toAccountId)
                        .queryParam("amount", amount)
                        .build(fromAccountId)
                )
                .retrieve(), BankOperationResponse.class
        );
    }

    @Override
    public ResponseEntity<List<BankOperationResponse>> getAllBankOperationsByTypeAndAccountId(long accountId, BankOperationType type) {
        return proxyRestService.proxyRequest(
                () -> restClient.post()
                .uri(builder -> builder
                        .path("/accounts/{accountId}/operations")
                        .queryParam("type", type)
                        .build(accountId)
                )
                .retrieve(), new ParameterizedTypeReference<List<BankOperationResponse>>() {}
        );
    }
}
