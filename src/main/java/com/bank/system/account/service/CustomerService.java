package com.bank.system.account.service;

import com.bank.system.account.model.Customer;
import com.bank.system.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;



    // Inyección por constructor (buena práctica)
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }





    // Crear cliente
    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Buscar todos los clientes
    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Buscar cliente por ID
    public Mono<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Buscar clientes por tipo (personal o empresarial)
    public Flux<Customer> getCustomersByType(String type) {
        return customerRepository.findByType(type);
    }

    // Actualizar cliente
    public Mono<Customer> updateCustomer(Long id, Customer customer) {
        return customerRepository.findById(id)
                .flatMap(existing -> {
                    existing.setFirstName(customer.getFirstName());
                    existing.setType(customer.getType());
                    return customerRepository.save(existing);
                });
    }

    // Eliminar cliente
    public Mono<Void> deleteCustomer(Long id) {
        return customerRepository.deleteById(id);
    }


}
