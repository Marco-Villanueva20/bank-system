package com.bank.system.account.controller;


import com.bank.system.account.model.Customer;
import com.bank.system.account.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final Sinks.Many<Customer> sink;

    // Inyección por constructor
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    // Crear cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer)
                .doOnNext(sink::tryEmitNext); // 🚀 emitir al stream
    }


    // Obtener todos
    @GetMapping
    public Flux<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> streamCustomers() {
        return customerService.getAllCustomers() // histórico
                .concatWith(sink.asFlux());     // + lo nuevo
    }



    // Obtener por ID
    @GetMapping("/{id}")
    public Mono<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    // Obtener por tipo (personal / empresarial)
    @GetMapping("/type/{type}")
    public Flux<Customer> getCustomersByType(@PathVariable String type) {
        return customerService.getCustomersByType(type);
    }

    // Actualizar
    @PutMapping("/{id}")
    public Mono<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }
}
