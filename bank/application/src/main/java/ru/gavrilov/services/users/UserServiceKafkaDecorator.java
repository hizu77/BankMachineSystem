package ru.gavrilov.services.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.gavrilov.contracts.users.UserService;
import ru.gavrilov.exceptions.users.InvalidFriendArgumentException;
import ru.gavrilov.exceptions.users.InvalidUserArgumentException;
import ru.gavrilov.models.users.User;
import ru.gavrilov.models.users.events.UserChangeEvent;
import ru.gavrilov.models.users.events.UserEventType;
import ru.gavrilov.models.users.properties.UserGender;
import ru.gavrilov.models.users.properties.UserHairColor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceKafkaDecorator implements UserService {
    private final UserService userService;
    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String USER_TOPIC="client-topic";


    @Override
    @Transactional
    public User createUser(User user) throws InvalidUserArgumentException {
        User createdUser = userService.createUser(user);
        Map<String, Object> userMap = objectMapper.convertValue(createdUser, Map.class);
        sendUserEvent(createdUser.getId(), UserEventType.CREATED, userMap);
        return createdUser;
    }

    @Override
    public User findUserById(long userId) {
        return userService.findUserById(userId);
    }

    @Override
    public List<User> findFriendsByUserId(long userId) {
        return userService.findFriendsByUserId(userId);
    }

    @Override
    public List<User> findAllByHairColorAndGender(UserHairColor color, UserGender gender) {
        return userService.findAllByHairColorAndGender(color, gender);
    }

    @Override
    @Transactional
    public User friendUsers(long userId, long friendId) throws InvalidUserArgumentException, InvalidFriendArgumentException {
        User user = userService.friendUsers(userId, friendId);
        Map<String, Object> userMap = new HashMap<String, Object>() {{
            put("newFriend", friendId);
        }};
        sendUserEvent(user.getId(), UserEventType.ADD_FRIEND, userMap);
        return user;
    }

    @Override
    @Transactional
    public User unfriendUsers(long userId, long friendId) throws InvalidUserArgumentException, InvalidFriendArgumentException {
        User user = userService.unfriendUsers(userId, friendId);
        Map<String, Object> userMap = new HashMap<String, Object>() {{
            put("removeFriend", friendId);
        }};
        sendUserEvent(user.getId(), UserEventType.REMOVE_FRIEND, userMap);
        return user;
    }

    private void sendUserEvent(long userId, UserEventType eventType, Map<String, Object> changes) {
        try {
            String changesJson = objectMapper.writeValueAsString(changes);

            UserChangeEvent event = UserChangeEvent.builder()
                    .userId(userId)
                    .eventType(eventType)
                    .changes(changesJson)
                    .date(LocalDateTime.now())
                    .build();

            String json = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(USER_TOPIC, userId, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
