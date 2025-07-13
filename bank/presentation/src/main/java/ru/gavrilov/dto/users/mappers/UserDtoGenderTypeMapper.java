package ru.gavrilov.dto.users.mappers;

import org.springframework.stereotype.Component;
import ru.gavrilov.dto.users.properties.UserDtoGenderType;
import ru.gavrilov.models.users.properties.UserGender;

@Component
public class UserDtoGenderTypeMapper {
    public UserGender toDomainAttribute(UserDtoGenderType gender) {
        return switch (gender) {
            case MAN -> UserGender.MAN;
            case WOMAN -> UserGender.WOMAN;
        };
    }

    public UserDtoGenderType toDtoAttribute(UserGender userGender) {
        return switch (userGender) {
            case MAN -> UserDtoGenderType.MAN;
            case WOMAN -> UserDtoGenderType.WOMAN;
        };
    }
}
