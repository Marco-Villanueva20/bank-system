package com.nttdata.ms_customer.domain.repository;

import com.nttdata.ms_customer.domain.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {
    Flux<Customer> getAll();
    Mono<Customer> getById(String id);
    Mono<Customer> create(Customer customer);
    Mono<Customer> update(Customer customer);
    Mono<Void> deleteById(String id);
}
