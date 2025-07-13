package ru.gavrilov.dto.users.mappers;

import org.springframework.stereotype.Component;
import ru.gavrilov.dto.users.properties.UserDtoHairColorType;
import ru.gavrilov.models.users.properties.UserHairColor;

@Component
public class UserDtoHairColorTypeMapper {
    public UserDtoHairColorType toDtoAttribute(UserHairColor userHairColor) {
        return switch (userHairColor) {
            case RED -> UserDtoHairColorType.RED;
            case GREEN -> UserDtoHairColorType.GREEN;
            case BLUE -> UserDtoHairColorType.BLUE;
            case BLACK -> UserDtoHairColorType.BLACK;
            case WHITE -> UserDtoHairColorType.WHITE;
            case YELLOW -> UserDtoHairColorType.YELLOW;
        };
    }

    public UserHairColor toDomainAttribute(UserDtoHairColorType userDtoHairColorType) {
        return switch (userDtoHairColorType) {
            case RED -> UserHairColor.RED;
            case GREEN -> UserHairColor.GREEN;
            case BLUE -> UserHairColor.BLUE;
            case BLACK -> UserHairColor.BLACK;
            case WHITE -> UserHairColor.WHITE;
            case YELLOW -> UserHairColor.YELLOW;
        };
    }
}
