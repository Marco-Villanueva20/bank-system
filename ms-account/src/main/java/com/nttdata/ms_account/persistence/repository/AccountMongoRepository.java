package com.nttdata.ms_account.persistence.repository;

import com.nttdata.ms_account.persistence.entity.AccountEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountMongoRepository extends ReactiveMongoRepository<AccountEntity, String> {
    Mono<AccountEntity> findByAccountNumber(String accountNumber);
    Flux<AccountEntity> findByCustomerId(String customerId);
    Mono<Long> countByCustomerIdAndAccountType(String customerId, String accountType);

}
