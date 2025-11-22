package com.nttdata.ms_account.domain.service;

import com.nttdata.ms_account.domain.exception.BusinessException;
import com.nttdata.ms_account.domain.model.Account;
import com.nttdata.ms_account.domain.model.Movement;
import com.nttdata.ms_account.domain.repository.AccountRepository;
import com.nttdata.ms_account.domain.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MovementService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;


    public Mono<Movement> deposit(Movement movement) {
        return accountRepository.getById(movement.getAccountId())
                .switchIfEmpty(Mono.error(new BusinessException("Account not found")))
                .flatMap(account -> switch (account.getAccountType()) {
                    case SAVINGS -> depositSaving(movement, account);
                    case CHECKING -> depositChecking(movement, account);
                    case FIXED_TERM -> depositFixedTerm(movement, account);
                });
    }


    private Mono<Movement> depositFixedTerm(Movement movement, Account account) {
        movement.requirePositive();
        account.validateFixedTermMovementAllowed();

        account.setBalance(account.getBalance().add(movement.getAmount()));
        account.setMonthlyMovementCount(account.getMonthlyMovementCount() + 1);

        movement.setDate(LocalDateTime.now());

        return accountRepository.update(account)
                .then(movementRepository.save(movement))
                .thenReturn(movement);
    }

    private Mono<Movement> depositChecking(Movement movement, Account account) {
        movement.requirePositive();
        account.setBalance(account.getBalance().add(movement.getAmount()));
        account.setMonthlyMovementCount(account.getMonthlyMovementCount() + 1);
        return accountRepository.update(account)
                .then(movementRepository.save(movement))
                .thenReturn(movement);
    }

    private Mono<Movement> depositSaving(Movement movement, Account account) {
        movement.requirePositive();
        account.validateSavingsMovementAllowed();

        account.setBalance(account.getBalance().add(movement.getAmount()));
        account.setMonthlyMovementCount(account.getMonthlyMovementCount() + 1);
        movement.setDate(LocalDateTime.now());


        return accountRepository.update(account)
                .then(movementRepository.save(movement))
                .thenReturn(movement);

    }

    public Mono<Movement> withdraw(Movement movement) {
        return accountRepository.getById(movement.getAccountId())
                .switchIfEmpty(Mono.error(new BusinessException("Account not found")))
                .flatMap(account -> switch (account.getAccountType()) {
                    case SAVINGS -> withdrawSaving(movement, account);
                    case CHECKING -> withdrawChecking(movement, account);
                    case FIXED_TERM -> withdrawFixedTerm(movement, account);
                });
    }

    private Mono<Movement> withdrawSaving(Movement movement, Account account) {
        movement.requirePositive();
        account.validateSavingsMovementAllowed();
        account.requireEnoughBalance(movement.getAmount());

        account.setBalance(account.getBalance().subtract(movement.getAmount()));
        account.setMonthlyMovementCount(account.getMonthlyMovementCount() + 1);
        movement.setDate(LocalDateTime.now());

        return accountRepository.update(account)
                .then(movementRepository.save(movement))
                .thenReturn(movement);
    }

    private Mono<Movement> withdrawChecking(Movement movement, Account account) {
        movement.requirePositive();
        account.requireEnoughBalance(movement.getAmount());

        account.setBalance(account.getBalance().subtract(movement.getAmount()));
        account.setMonthlyMovementCount(account.getMonthlyMovementCount() + 1);
        movement.setDate(LocalDateTime.now());

        return accountRepository.update(account)
                .then(movementRepository.save(movement))
                .thenReturn(movement);
    }

    private Mono<Movement> withdrawFixedTerm(Movement movement, Account account) {
        movement.requirePositive();
        account.validateFixedTermMovementAllowed();
        account.requireEnoughBalance(movement.getAmount());

        account.setBalance(account.getBalance().subtract(movement.getAmount()));
        account.setMonthlyMovementCount(account.getMonthlyMovementCount() + 1);
        movement.setDate(LocalDateTime.now());

        return accountRepository.update(account)
                .then(movementRepository.save(movement))
                .thenReturn(movement);
    }


}
