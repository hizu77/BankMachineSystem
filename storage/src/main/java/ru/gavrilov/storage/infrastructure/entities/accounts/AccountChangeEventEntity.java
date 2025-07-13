package ru.gavrilov.storage.infrastructure.entities.accounts;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_events")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AccountChangeEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private AccountEventType eventType;

    @Lob
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private String changes;

    private LocalDateTime date;
}
