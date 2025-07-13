package ru.gavrilov.models.users;

import lombok.*;
import ru.gavrilov.models.users.properties.UserGender;
import ru.gavrilov.models.users.properties.UserHairColor;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
public class User
{
    private long id;
    private String login;
    private String name;
    private int age;
    private UserGender gender;
    private UserHairColor hairColor;
    private Set<Long> friendsId;
}