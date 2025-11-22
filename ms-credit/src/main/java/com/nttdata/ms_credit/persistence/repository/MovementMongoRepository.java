package com.nttdata.ms_credit.persistence.repository;

import com.nttdata.ms_credit.persistence.entity.MovementEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovementMongoRepository extends ReactiveMongoRepository<MovementEntity, String> {
}
