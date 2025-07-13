package ru.gavrilov.models.users.mappers;

import org.springframework.stereotype.Component;
import ru.gavrilov.entities.users.properties.UserHairColorDb;
import ru.gavrilov.models.users.properties.UserHairColor;

@Component
public class UserHairColorMapper {
    public UserHairColor toDomainAttribute(UserHairColorDb hairColor) {
        return switch (hairColor) {
            case RED -> UserHairColor.RED;
            case BLUE -> UserHairColor.BLUE;
            case YELLOW -> UserHairColor.YELLOW;
            case GREEN -> UserHairColor.GREEN;
            case WHITE -> UserHairColor.WHITE;
            case BLACK -> UserHairColor.BLACK;
        };
    }

    public UserHairColorDb toDbAttribute(UserHairColor hairColor) {
        return switch (hairColor) {
            case RED -> UserHairColorDb.RED;
            case BLUE -> UserHairColorDb.BLUE;
            case YELLOW -> UserHairColorDb.YELLOW;
            case GREEN -> UserHairColorDb.GREEN;
            case WHITE -> UserHairColorDb.WHITE;
            case BLACK -> UserHairColorDb.BLACK;
        };
    }
}
