package ru.gavrilov.dto.users.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gavrilov.dto.users.UserCreateRequest;
import ru.gavrilov.models.users.User;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class UserCreateRequestMapper {
    private final UserDtoHairColorTypeMapper hairColorTypeMapper;
    private final UserDtoGenderTypeMapper genderTypeMapper;

    public User toModel(UserCreateRequest user) {
        return User.builder()
                .gender(genderTypeMapper.toDomainAttribute(user.getGender()))
                .age(user.getAge())
                .login(user.getLogin())
                .name(user.getName())
                .hairColor(hairColorTypeMapper.toDomainAttribute(user.getHairColor()))
                .friendsId(new HashSet<>())
                .build();
    }
}
