package com.nttdata.ms_account.persistence.repository;

import com.nttdata.ms_account.persistence.entity.MovementEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface MovementMongoRepository extends ReactiveMongoRepository<MovementEntity,String> {
}
