package ru.gavrilov.controllers.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gavrilov.contracts.users.UserService;
import ru.gavrilov.dto.users.UserCreateRequest;
import ru.gavrilov.dto.users.UserResponse;
import ru.gavrilov.dto.users.mappers.UserCreateRequestMapper;
import ru.gavrilov.dto.users.mappers.UserDtoGenderTypeMapper;
import ru.gavrilov.dto.users.mappers.UserDtoHairColorTypeMapper;
import ru.gavrilov.dto.users.mappers.UserResponseMapper;
import ru.gavrilov.dto.users.properties.UserDtoGenderType;
import ru.gavrilov.dto.users.properties.UserDtoHairColorType;
import ru.gavrilov.exceptions.users.InvalidUserArgumentException;
import ru.gavrilov.exceptions.users.UserException;
import ru.gavrilov.models.users.User;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserCreateRequestMapper userCreateRequestMapper;
    private final UserResponseMapper userResponseMapper;
    private final UserDtoHairColorTypeMapper userDtoHairColorTypeMapper;
    private final UserDtoGenderTypeMapper userDtoGenderTypeMapper;

    @Operation(
            summary = "Create user",
            tags = "Users",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created"),
                    @ApiResponse(responseCode = "400", description = "Incorrect user arguments")
            }
    )
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        User user;

        try {
            user = userService.createUser(userCreateRequestMapper.toModel(request));
        }
        catch (InvalidUserArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseMapper.toResponse(user));
    }

    @Operation(
            summary = "Get user by id",
            tags = "Users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable("id") long id) {
        User user = userService.findUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userResponseMapper.toResponse(user));
    }

    @Operation(
            summary = "Get all friends by user id",
            tags = "Users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Friends list found")
            }
    )
    @GetMapping("/users/{userId}/friends")
    public ResponseEntity<List<UserResponse>> getAllFriendsByUserId(
            @PathVariable("userId") long userId
    ) {
        var friends = userService.findFriendsByUserId(userId).stream()
                .map(userResponseMapper::toResponse).toList();

        return ResponseEntity.ok(friends);
    }

    @Operation(
            summary = "Get all users by hair color and gender",
            tags = "Users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users list found")
            }
    )
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsersByHairColorAndGender(
        @RequestParam("hair-color") UserDtoHairColorType hairColor,
        @RequestParam("gender") UserDtoGenderType gender
    ) {
        var users = userService.findAllByHairColorAndGender(
                userDtoHairColorTypeMapper.toDomainAttribute(hairColor),
                userDtoGenderTypeMapper.toDomainAttribute(gender)).stream()
                .map(userResponseMapper::toResponse).toList();

        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Add friend to user by id",
            tags = "Users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful friend adding"),
                    @ApiResponse(responseCode = "404", description = "Incorrect users id")
            }
    )
    @PostMapping("/users/{userId}/friends")
    public ResponseEntity<UserResponse> addUserFriend(
            @PathVariable("userId") long userId,
            @RequestParam("friend-id") long friendId) {
        User user;

        try {
            user = userService.friendUsers(userId, friendId);
        } catch (UserException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userResponseMapper.toResponse(user));
    }

    @Operation(
            summary = "Delete user friend by id",
            tags = "Users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful friend deleting"),
                    @ApiResponse(responseCode = "404", description = "Incorrect users id")
            }
    )
    @DeleteMapping("/users/{userId}/friends")
    public ResponseEntity<UserResponse> removeUserFriend(
            @PathVariable("userId") long userId,
            @RequestParam("friend-id") long friendId) {
        User user;

        try {
            user = userService.unfriendUsers(userId, friendId);
        }
        catch (UserException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userResponseMapper.toResponse(user));
    }
}
