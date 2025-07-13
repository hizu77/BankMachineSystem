package ru.gavrilov.models.users.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gavrilov.dao.users.UserDao;
import ru.gavrilov.entities.users.UserEntity;
import ru.gavrilov.models.users.User;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final UserGenderMapper genderMapper;
    private final UserHairColorMapper hairColorMapper;
    private final UserDao userDao;

    public User toDomain(UserEntity userEntity) {
        var friends = userEntity.getFriends().stream()
                .map(UserEntity::getId)
                .collect(Collectors.toSet());

        return User.builder()
                .id(userEntity.getId())
                .login(userEntity.getLogin())
                .name(userEntity.getName())
                .age(userEntity.getAge())
                .gender(genderMapper.toDomainAttribute(userEntity.getGender()))
                .hairColor(hairColorMapper.toDomainAttribute(userEntity.getHairColor()))
                .friendsId(friends)
                .build();
    }

    public UserEntity toEntity(User user) {
        var friends = user.getFriendsId().stream()
                .map(id -> userDao.findById(id.longValue()))
                .collect(Collectors.toSet());

        return UserEntity.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .age(user.getAge())
                .gender(genderMapper.toDbAttribute(user.getGender()))
                .hairColor(hairColorMapper.toDbAttribute(user.getHairColor()))
                .friends(friends)
                .build();
    }
}
