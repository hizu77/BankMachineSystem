package ru.gavrilov.bankapigateway.application.security.contracts;

public interface SecurityService {
    boolean hasBankAccountAccess(long targetAccountId);
    boolean hasBankUserAccess(long targetUserId);
}
