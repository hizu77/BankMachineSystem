package ru.gavrilov.bankapigateway.application.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.gavrilov.bankapigateway.application.bankAccounts.clients.BankAccountClient;
import ru.gavrilov.bankapigateway.application.bankAccounts.dto.BankAccountResponse;
import ru.gavrilov.bankapigateway.application.security.contracts.SecurityService;
import ru.gavrilov.bankapigateway.infrastructure.users.entities.UserEntity;
import ru.gavrilov.bankapigateway.infrastructure.users.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service("securityService")
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final BankAccountClient bankAccountClient;
    private final UserRepository userRepository;

    public boolean hasBankAccountAccess(long targetAccountId) {
        Optional<UserEntity> user = getAuthenticatedUser();

        if (user.isEmpty()) {
            return false;
        }

        long bankUserId = user.get().getBankUserId();
        ResponseEntity<List<BankAccountResponse>> accounts =
                bankAccountClient.getAllBankAccountsByUserId(bankUserId);

        if (!accounts.getStatusCode().is2xxSuccessful() || accounts.getBody() == null) {
            return false;
        }

        return accounts.getBody().stream().anyMatch(
                acc -> acc.getId() == targetAccountId);
    }

    public boolean hasBankUserAccess(long targetAccountId) {
        Optional<UserEntity> user = getAuthenticatedUser();

        return user.filter(userEntity -> userEntity.getBankUserId() == targetAccountId).isPresent();
    }

    private Optional<UserEntity> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return Optional.empty();
        }

        return userRepository.findByLogin(authentication.getName());
    }
}
