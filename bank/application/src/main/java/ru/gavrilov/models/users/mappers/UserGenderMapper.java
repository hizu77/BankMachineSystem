package ru.gavrilov.models.users.mappers;

import org.springframework.stereotype.Component;
import ru.gavrilov.entities.users.properties.UserGenderDb;
import ru.gavrilov.models.users.properties.UserGender;

@Component
public class UserGenderMapper {
    public UserGender toDomainAttribute(UserGenderDb gender) {
        return switch (gender) {
            case MAN -> UserGender.MAN;
            case WOMAN -> UserGender.WOMAN;
        };
    }

    public UserGenderDb toDbAttribute(UserGender gender) {
        return switch (gender) {
            case MAN -> UserGenderDb.MAN;
            case WOMAN -> UserGenderDb.WOMAN;
        };
    }
}
