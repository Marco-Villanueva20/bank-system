package com.nttdata.ms_customer.domain.service;


import com.nttdata.ms_customer.domain.model.Customer;
import com.nttdata.ms_customer.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


//Service recibe el customer del Controller
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Flux<Customer> getAll() {
        return customerRepository.getAll();
    }

    public Mono<Customer> create(Customer customer) {
        return customerRepository.create(customer);
    }

    public Mono<Customer> update(Customer customer) {
        return customerRepository.getById(customer.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("Cliente no encontrado")))
                .flatMap(existing -> {
                            existing.setFirstname(customer.getFirstname());
                            existing.setLastname(customer.getLastname());
                            existing.setCustomerType(customer.getCustomerType());
                            existing.setDocumentNumber(customer.getDocumentNumber());
                            existing.setEmail(customer.getEmail());
                            existing.setPhone(customer.getPhone());
                            return customerRepository.update(existing);
                        }
                );
    }

    public Mono<Customer> getById(String id) {
        return customerRepository.getById(id);
    }

    public Mono<Void> deleteById(String id) {
        return customerRepository.getById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El id no pertenece a ningún customer")))
                .flatMap(customer -> customerRepository.deleteById(customer.getId()));
    }


}
