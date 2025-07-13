package ru.gavrilov.entities.operations;

import jakarta.persistence.*;
import lombok.*;
import ru.gavrilov.entities.accounts.AccountEntity;
import ru.gavrilov.entities.operations.properties.OperationTypeDb;

import java.time.LocalDateTime;

@Entity
@Table(name = "operations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OperationEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Enumerated(EnumType.STRING)
        @Column(name = "operation_type")
        private OperationTypeDb operationType;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "account_id")
        private AccountEntity account;

        @Column(name = "date_time")
        private LocalDateTime dateTime;
}