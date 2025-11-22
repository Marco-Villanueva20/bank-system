package com.nttdata.ms_account.persistence;

import com.nttdata.ms_account.domain.model.Account;
import com.nttdata.ms_account.domain.repository.AccountRepository;
import com.nttdata.ms_account.persistence.mapper.AccountMapper;
import com.nttdata.ms_account.persistence.repository.AccountMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountMongoRepository accountMongoRepository;
    private final AccountMapper accountMapper;

    @Override
    public Flux<Account> getAll() {
        return accountMongoRepository.findAll().map(accountMapper::toDomain);
    }

    @Override
    public Mono<Account> getById(String id) {
        return accountMongoRepository.findById(id).map(accountMapper::toDomain);
    }

    @Override
    public Mono<Account> create(Account domain) {
        return accountMongoRepository.save(accountMapper.toEntity(domain)).map(accountMapper::toDomain);
    }

    @Override
    public Mono<Account> update(Account domain) {
        return accountMongoRepository.save(accountMapper.toEntity(domain)).map(accountMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return accountMongoRepository.deleteById(id);
    }

    @Override
    public Flux<Account> findByCustomerId(String customerId) {
        return accountMongoRepository.findByCustomerId(customerId).map(accountMapper::toDomain);
    }
}
