package com.nttdata.ms_credit.domain.repository;

import com.nttdata.ms_credit.domain.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementRepository {
    Flux<Movement> findAll();
    Mono<Movement> findById(String id);
    Mono<Movement> create(Movement movement);
    Mono<Movement> update(Movement movement);
    Mono<Void> deleteById(String id);
}
