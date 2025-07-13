package ru.gavrilov.bankapigateway.application.bankUsers.dto;

import lombok.Builder;
import lombok.Data;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.properties.BankUserGenderType;
import ru.gavrilov.bankapigateway.application.bankUsers.dto.properties.BankUserHairColorType;

import java.util.Set;

@Data
@Builder
public class BankUserResponse {
    private final long id;
    private final String login;
    private final String name;
    private final int age;
    private final BankUserGenderType gender;
    private final BankUserHairColorType hairColor;
    private final Set<Long> friendsId;
}
