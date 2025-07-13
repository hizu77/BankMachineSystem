package ru.gavrilov.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gavrilov.dto.users.properties.UserDtoGenderType;
import ru.gavrilov.dto.users.properties.UserDtoHairColorType;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String login;
    private String name;
    private int age;
    private UserDtoGenderType gender;
    private UserDtoHairColorType hairColor;
    private Set<Long> friendsId;
}
