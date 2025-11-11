package com.nttdata.ms_customer.persistence;

import com.nttdata.ms_customer.domain.dto.CustomerResponseDTO;
import com.nttdata.ms_customer.domain.model.Customer;
import com.nttdata.ms_customer.domain.repository.CustomerRepository;
import com.nttdata.ms_customer.persistence.entity.CustomerEntity;
import com.nttdata.ms_customer.persistence.mapper.CustomerMapper;
import com.nttdata.ms_customer.persistence.repository.CustomerMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerMongoRepository customerMongoRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Flux<Customer> getAll() {
        return customerMongoRepository.findAll().map(customerMapper::toDomain);
    }

    @Override
    public Mono<Customer> getById(String id) {
        return customerMongoRepository.findById(id).map(customerMapper::toDomain);
    }

    @Override
    public Mono<Customer> create(Customer customer) {
        CustomerEntity entity = customerMapper.toEntity(customer);
        return customerMongoRepository.save(entity).map(customerMapper::toDomain);
    }

    @Override
    public Mono<Customer> update(Customer customer) {
        CustomerEntity entity = customerMapper.toEntity(customer);
        return customerMongoRepository.save(entity).map(customerMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return customerMongoRepository.deleteById(id);
    }
}
