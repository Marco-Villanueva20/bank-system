package com.nttdata.ms_account.domain.repository;

import com.nttdata.ms_account.domain.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Flux<Account> getAll();
    Mono<Account> getById(String id);
    Mono<Account> create(Account domain);
    Mono<Account> update(Account domain);
    Mono<Void> deleteById(String id);
    Flux<Account> findByCustomerId(String customerId);
}
