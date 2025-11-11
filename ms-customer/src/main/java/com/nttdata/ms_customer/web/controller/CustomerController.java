package com.nttdata.ms_customer.web.controller;

import com.nttdata.ms_customer.domain.dto.CustomerRequestDTO;
import com.nttdata.ms_customer.domain.dto.CustomerResponseDTO;
import com.nttdata.ms_customer.domain.model.Customer;
import com.nttdata.ms_customer.domain.service.CustomerService;
import com.nttdata.ms_customer.persistence.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping
    public Flux<CustomerResponseDTO> getAll() {
        return customerService.getAll().map(customerMapper::toResponseDto);
    }

    @PostMapping
    public Mono<CustomerResponseDTO> create(@Validated @RequestBody CustomerRequestDTO requestDTO) {
        Customer domain = customerMapper.toDomain(requestDTO);
        return customerService.create(domain).map(customerMapper::toResponseDto
        );
    }

    @GetMapping("/{id}")
    public Mono<CustomerResponseDTO> getById(@PathVariable String id) {
        return customerService.getById(id).map(customerMapper::toResponseDto);
    }

    @PutMapping("/{id}")
    public Mono<CustomerResponseDTO> update(
            @PathVariable String id,
            @Validated @RequestBody CustomerRequestDTO requestDTO) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El id no puede ser vacío o nulo");
        }

        Customer domain = customerMapper.toDomain(requestDTO);
        domain.setId(id);

        return customerService.update(domain)
                .map(customerMapper::toResponseDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return customerService.deleteById(id);
    }


}
