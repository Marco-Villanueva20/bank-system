package com.nttdata.ms_credit.domain.service;

import com.nttdata.ms_credit.domain.dto.BalanceResponseDTO;
import com.nttdata.ms_credit.domain.model.Credit;
import com.nttdata.ms_credit.domain.repository.CreditRepository;
import com.nttdata.ms_credit.infrastructure.client.CustomerClient;
import com.nttdata.ms_credit.infrastructure.client.dto.CustomerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;
    private final CustomerClient customerClient;

    public Mono<Credit> create(Credit credit) {
        return customerClient.getCustomerById(credit.getCustomerId())
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")))
                .flatMap(customer ->
                        switch (credit.getCreditType()) {
                            case PERSONAL -> createPersonalCredit(customer, credit);
                            case BUSINESS -> createBusinessCredit(customer, credit);
                            case CREDIT_CARD -> createCreditCard(customer, credit);
                        }
                );
    }

    private Mono<Credit> createCreditCard(CustomerResponseDTO customer, Credit credit) {
        if (credit.getCreditLimit() == null || credit.getCreditLimit().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new RuntimeException("Credit card requires a positive credit limit"));
        }
        return creditRepository.create(credit);
    }

    private Mono<Credit> createBusinessCredit(CustomerResponseDTO customer, Credit credit) {
        if (!"BUSINESS".equalsIgnoreCase(customer.getCustomerType())) {
            return Mono.error(new RuntimeException("Customer is not BUSINESS"));
        }

        return creditRepository.create(credit);
    }

    public Mono<BalanceResponseDTO> getAvailableBalance(String creditId) {
        return creditRepository.getAvailableBalance(creditId);
    }

    private Mono<Credit> createPersonalCredit(CustomerResponseDTO customer, Credit credit) {
        if (!"PERSONAL".equalsIgnoreCase(customer.getCustomerType())) {
            return Mono.error(new RuntimeException("Customer is not PERSONAL"));
        }

        return creditRepository.findByCustomerId(customer.getId())
                .count()
                .flatMap(count -> {
                    if (count > 0) {
                        return Mono.error(new RuntimeException("The client already has a credit"));
                    }
                    return creditRepository.create(credit);
                });

    }

    public Flux<Credit> findAll() {
        return creditRepository.findAll();
    }

    public Mono<Credit> findById(String id) {
        return creditRepository.findById(id);
    }

    public  Flux<Credit> findByCustomerId(String customerId) {
        return creditRepository.findByCustomerId(customerId);
    }
}
