package ru.gavrilov.dto.users.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gavrilov.dto.users.UserResponse;
import ru.gavrilov.models.users.User;

@Component
@RequiredArgsConstructor
public class UserResponseMapper {
    private final UserDtoHairColorTypeMapper hairColorTypeMapper;
    private final UserDtoGenderTypeMapper genderTypeMapper;

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .gender(genderTypeMapper.toDtoAttribute(user.getGender()))
                .age(user.getAge())
                .login(user.getLogin())
                .name(user.getName())
                .hairColor(hairColorTypeMapper.toDtoAttribute(user.getHairColor()))
                .friendsId(user.getFriendsId())
                .build();
    }
}
