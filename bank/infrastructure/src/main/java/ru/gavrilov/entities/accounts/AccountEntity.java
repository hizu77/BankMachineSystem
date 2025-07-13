package ru.gavrilov.entities.accounts;

import jakarta.persistence.*;
import lombok.*;
import ru.gavrilov.entities.operations.OperationEntity;
import ru.gavrilov.entities.users.UserEntity;

import java.util.List;

@Entity
@Table(name = "accounts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<OperationEntity> operations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    private double balance;
}