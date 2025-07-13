package ru.gavrilov.storage.infrastructure.entities.users;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangeEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private UserEventType eventType;

    @Lob
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private String changes;

    private LocalDateTime date;
}
