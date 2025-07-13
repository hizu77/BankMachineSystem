package ru.gavrilov.dao.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gavrilov.entities.users.UserEntity;
import ru.gavrilov.entities.users.properties.UserGenderDb;
import ru.gavrilov.entities.users.properties.UserHairColorDb;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {
    UserEntity findById(long id);

    @Query("SELECT u.friends from user u where u.id = ?1")
    List<UserEntity> findByFriendsId(long userId);

    List<UserEntity> findByHairColorAndGender(UserHairColorDb color, UserGenderDb gender);
}