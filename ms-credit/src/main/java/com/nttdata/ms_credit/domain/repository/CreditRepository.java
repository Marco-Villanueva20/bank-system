package com.nttdata.ms_credit.domain.repository;

import com.nttdata.ms_credit.domain.dto.BalanceResponseDTO;
import com.nttdata.ms_credit.domain.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditRepository {

    //== CRUD
        // CRUD
        Mono<Credit> create(Credit credit);
        Flux<Credit> findAll();
        Mono<Credit> findById(String id);
        Mono<Credit> update(Credit credit);
        Mono<Void> deleteById(String id);

        // consultas específicas
        Flux<Credit> findByCustomerId(String customerId);

        // devuelve solo el balance disponible (o un DTO con detalles)
        Mono<BalanceResponseDTO> getAvailableBalance(String creditId);
    }

