package ru.gavrilov.dao.operations;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gavrilov.entities.operations.OperationEntity;
import ru.gavrilov.entities.operations.properties.OperationTypeDb;

import java.util.List;

public interface OperationDao extends JpaRepository<OperationEntity, Long> {
    OperationEntity findById(long id);

    List<OperationEntity> findByOperationTypeAndAccountId(
            OperationTypeDb operationType, long accountId
    );
}

