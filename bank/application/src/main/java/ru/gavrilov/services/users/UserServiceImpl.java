package ru.gavrilov.services.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gavrilov.contracts.users.UserService;
import ru.gavrilov.dao.users.UserDao;
import ru.gavrilov.exceptions.users.InvalidFriendArgumentException;
import ru.gavrilov.exceptions.users.InvalidUserArgumentException;
import ru.gavrilov.models.users.User;
import ru.gavrilov.models.users.mappers.UserGenderMapper;
import ru.gavrilov.models.users.mappers.UserHairColorMapper;
import ru.gavrilov.models.users.mappers.UserMapper;
import ru.gavrilov.models.users.properties.UserGender;
import ru.gavrilov.models.users.properties.UserHairColor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final UserHairColorMapper userHairColorMapper;
    private final UserGenderMapper userGenderMapper;

    @Override
    public User createUser(User user) throws InvalidUserArgumentException {
        if (user.getAge() < 0) {
            throw new InvalidUserArgumentException("Age must be at least 12");
        }

        var userEntity = userDao.save(userMapper.toEntity(user));

        return userMapper.toDomain(userEntity);
    }

    @Override
    public User findUserById(long userId) {
        var userEntity = userDao.findById(userId);

        if (userEntity == null) {
            return null;
        }

        return userMapper.toDomain(userEntity);
    }

    @Override
    public List<User> findFriendsByUserId(long userId) {
        return userDao.findByFriendsId(userId).stream()
                .map(userMapper::toDomain).toList();
    }

    @Override
    public List<User> findAllByHairColorAndGender(UserHairColor color, UserGender gender) {
        return userDao.findByHairColorAndGender(
                userHairColorMapper.toDbAttribute(color),
                userGenderMapper.toDbAttribute(gender)
        ).stream().map(userMapper::toDomain).toList();
    }

    @Override
    public User friendUsers(long userId, long friendId)
            throws InvalidUserArgumentException, InvalidFriendArgumentException {
        var user = userDao.findById(userId);
        var friend = userDao.findById(friendId);

        if (user == null) {
            throw new InvalidUserArgumentException("User not found");
        }

        if (friend == null) {
            throw new InvalidFriendArgumentException("Friend not found");
        }

        user.getFriends().add(friend);

        return userMapper.toDomain(userDao.save(user));
    }

    @Override
    public User unfriendUsers(long userId, long friendId)
            throws InvalidUserArgumentException, InvalidFriendArgumentException {
        var user = userDao.findById(userId);
        var friend = userDao.findById(friendId);

        if (user == null) {
            throw new InvalidUserArgumentException("User not found");
        }

        if (friend == null || !user.getFriends().contains(friend)) {
            throw new InvalidFriendArgumentException("Friend not found");
        }

        user.getFriends().remove(friend);

        return userMapper.toDomain(userDao.save(user));
    }
}