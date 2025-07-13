package ru.gavrilov.entities.users;

import jakarta.persistence.*;
import lombok.*;
import ru.gavrilov.entities.users.properties.UserGenderDb;
import ru.gavrilov.entities.users.properties.UserHairColorDb;

import java.util.Set;


@Entity(name = "user")
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private UserGenderDb gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "hair_color")
    private UserHairColorDb hairColor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<UserEntity> friends;

    private String login;
    private String name;
    private int age;
}