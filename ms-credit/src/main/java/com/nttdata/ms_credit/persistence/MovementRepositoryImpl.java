package com.nttdata.ms_credit.persistence;

import com.nttdata.ms_credit.domain.model.Movement;
import com.nttdata.ms_credit.domain.repository.MovementRepository;
import com.nttdata.ms_credit.persistence.mapper.MovementMapper;
import com.nttdata.ms_credit.persistence.repository.MovementMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MovementRepositoryImpl implements MovementRepository {
    private final MovementMongoRepository movementMongoRepository;
    private final MovementMapper movementMapper;

    @Override
    public Flux<Movement> findAll() {
        return movementMongoRepository.findAll().map(movementMapper::toDomain);
    }

    @Override
    public Mono<Movement> findById(String id) {
        return movementMongoRepository.findById(id).map(movementMapper::toDomain);
    }

    @Override
    public Mono<Movement> create(Movement movement) {
        return movementMongoRepository.save(movementMapper.toEntity(movement)).map(movementMapper::toDomain);
    }

    @Override
    public Mono<Movement> update(Movement movement) {
        return movementMongoRepository.save(movementMapper.toEntity(movement)).map(movementMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return movementMongoRepository.deleteById(id);
    }
}
