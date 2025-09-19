package com.bank.system.account.repository;

import com.bank.system.account.model.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
    Flux<Customer> findByType(String type);
}
