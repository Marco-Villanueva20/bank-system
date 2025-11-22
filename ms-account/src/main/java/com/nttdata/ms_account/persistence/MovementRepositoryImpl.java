package com.nttdata.ms_account.persistence;

import com.nttdata.ms_account.domain.model.Movement;
import com.nttdata.ms_account.domain.repository.MovementRepository;
import com.nttdata.ms_account.persistence.mapper.MovementMapper;
import com.nttdata.ms_account.persistence.repository.MovementMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MovementRepositoryImpl implements MovementRepository {
    private final MovementMongoRepository movementMongoRepository;
    private final MovementMapper movementMapper;

    @Override
    public Mono<Movement> save(Movement movement) {
        return movementMongoRepository.save(movementMapper.toEntity(movement)).map(movementMapper::toDomain);
    }
}
