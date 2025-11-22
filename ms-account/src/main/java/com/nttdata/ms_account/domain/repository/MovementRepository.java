package com.nttdata.ms_account.domain.repository;

import com.nttdata.ms_account.domain.model.Movement;
import reactor.core.publisher.Mono;

public interface MovementRepository {
    Mono<Movement> save(Movement movement);
}
