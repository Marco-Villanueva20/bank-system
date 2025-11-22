package com.nttdata.ms_credit.web.controller;

import com.nttdata.ms_credit.domain.dto.BalanceResponseDTO;
import com.nttdata.ms_credit.domain.dto.CreditRequestDTO;
import com.nttdata.ms_credit.domain.dto.CreditResponseDto;
import com.nttdata.ms_credit.domain.model.Credit;
import com.nttdata.ms_credit.domain.service.CreditService;
import com.nttdata.ms_credit.persistence.mapper.CreditMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/credits")
@RequiredArgsConstructor
public class CreditController {
    private final CreditService creditService;
    private final CreditMapper creditMapper; // domain <-> DTO

    @GetMapping
    public Flux<CreditResponseDto> getAll() {
        return creditService.findAll().map(creditMapper::toResponseDTO);
    }

    @GetMapping("/{id}")
    public Mono<CreditResponseDto> getById(@PathVariable String id) {
        return creditService.findById(id).map(creditMapper::toResponseDTO);
    }

    @PostMapping
    public Mono<CreditResponseDto> create(@RequestBody CreditRequestDTO request) {
        Credit domain = creditMapper.toDomain(request);
        return creditService.create(domain).map(creditMapper::toResponseDTO);
    }

    @GetMapping("/by-customer/{customerId}")
    public Flux<CreditResponseDto> getByCustomer(@PathVariable String customerId) {
        return creditService.findByCustomerId(customerId).map(creditMapper::toResponseDTO);
    }

    // saldo disponible (tarjeta o crédito)
    @GetMapping("/{id}/balance")
    public Mono<BalanceResponseDTO> getAvailableBalance(@PathVariable String id) {
        return creditService.getAvailableBalance(id);
    }
}
