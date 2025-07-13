package ru.gavrilov.models.accounts;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Account {
    private final long id;
    private double balance;
    private final long ownerId;
    private final List<Long> operationsId;
}