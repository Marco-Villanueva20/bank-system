package com.nttdata.ms_credit.persistence;

import com.nttdata.ms_credit.domain.dto.BalanceResponseDTO;
import com.nttdata.ms_credit.domain.model.Credit;
import com.nttdata.ms_credit.domain.model.CreditType;
import com.nttdata.ms_credit.domain.repository.CreditRepository;
import com.nttdata.ms_credit.persistence.mapper.CreditMapper;
import com.nttdata.ms_credit.persistence.repository.CreditMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class CreditRepositoryImpl implements CreditRepository {
    private final CreditMongoRepository creditMongoRepository;
    private final CreditMapper creditMapper;


    @Override
    public Mono<Credit> create(Credit credit) {
        return creditMongoRepository.save(creditMapper.toEntity(credit)).map(creditMapper::toDomain);
    }

    @Override
    public Flux<Credit> findAll() {
        return creditMongoRepository.findAll().map(creditMapper::toDomain);
    }

    @Override
    public Mono<Credit> findById(String id) {
        return creditMongoRepository.findById(id).map(creditMapper::toDomain);
    }

    @Override
    public Mono<Credit> update(Credit credit) {
        return creditMongoRepository.save(creditMapper.toEntity(credit)).map(creditMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return creditMongoRepository.deleteById(id);
    }

    @Override
    public Flux<Credit> findByCustomerId(String customerId) {
        return creditMongoRepository.findByCustomerId(customerId).map(creditMapper::toDomain);
    }

    @Override
    public Mono<BalanceResponseDTO> getAvailableBalance(String creditId) {
        return creditMongoRepository.findById(creditId)
                .switchIfEmpty(Mono.error(new RuntimeException("Credit not found")))
                .map(credit -> BalanceResponseDTO.builder()
                        .productId(credit.getId())
                        .availableBalance(calculateAvailable(creditMapper.toDomain(credit)))
                        .build());
    }

    private BigDecimal calculateAvailable(Credit credit) {
        // Para tarjetas de crédito
        if (credit.getCreditType() == CreditType.CREDIT_CARD) {
            return credit.getCreditLimit().subtract(credit.getConsumedAmount());
        }

        // Para créditos normales
        return credit.getOutstandingAmount();
    }

}
