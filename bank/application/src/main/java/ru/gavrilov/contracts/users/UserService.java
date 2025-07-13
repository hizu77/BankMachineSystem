package ru.gavrilov.contracts.users;

import ru.gavrilov.exceptions.users.InvalidFriendArgumentException;
import ru.gavrilov.exceptions.users.InvalidUserArgumentException;
import ru.gavrilov.models.users.properties.UserGender;
import ru.gavrilov.models.users.properties.UserHairColor;
import ru.gavrilov.models.users.User;

import java.util.List;

public interface UserService {
    User createUser(User user) throws InvalidUserArgumentException;

    User findUserById(long userId);

    List<User> findFriendsByUserId(long userId);

    List<User> findAllByHairColorAndGender(UserHairColor color, UserGender gender);

    User friendUsers(long userId, long friendId)
            throws InvalidUserArgumentException, InvalidFriendArgumentException;

    User unfriendUsers(long userId, long friendId)
            throws InvalidUserArgumentException, InvalidFriendArgumentException;
}
