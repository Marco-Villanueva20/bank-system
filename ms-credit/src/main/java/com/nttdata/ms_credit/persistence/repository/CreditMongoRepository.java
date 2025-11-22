package com.nttdata.ms_credit.persistence.repository;

import com.nttdata.ms_credit.persistence.entity.CreditEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CreditMongoRepository extends ReactiveMongoRepository<CreditEntity, String> {
    Flux<CreditEntity> findByCustomerId(String customerId);
}
