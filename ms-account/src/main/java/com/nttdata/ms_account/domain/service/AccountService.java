package com.nttdata.ms_account.domain.service;

import com.nttdata.ms_account.domain.model.Account;
import com.nttdata.ms_account.domain.model.AccountType;
import com.nttdata.ms_account.domain.model.CustomerType;
import com.nttdata.ms_account.domain.repository.AccountRepository;
import com.nttdata.ms_account.infrastructure.client.CustomerClient;
import com.nttdata.ms_account.persistence.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerClient customerClient;
    // si más adelante validas el tipo de customer, inyecta aquí un cliente http (WebClient) para ms_customer

    public Flux<Account> getAll(){
        return accountRepository.getAll();
    }

    public Mono<Account> createAccount(Account account) {
        return customerClient.getCustomerById(account.getCustomerId())
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")))
                .flatMap(customer -> {

                    if (customer.getCustomerType().equals(CustomerType.BUSINESS)) {
                        if (account.getAccountType() != AccountType.CHECKING) {
                            return Mono.error(new RuntimeException("Only CHECKING allowed for business customers"));
                        }
                    }

                    // Aquí ya todo OK
                    return accountRepository.create(account);
                });
    }


    public Mono<Account> getById(String id) {
        return accountRepository.getById(id);
    }

    public Mono<Account> create(Account domain) {
        // Validaciones simples que solo requieren el domain
        if (domain.getAccountType() == AccountType.FIXED_TERM && domain.getFixedDay() == null) {
            return Mono.error(new IllegalArgumentException("Fixed term accounts require fixedDay"));
        }

        // holders/signatories solo aplican a cuentas CHECKING (corriente empresarial)
        if (hasHoldersOrSignatories(domain) && domain.getAccountType() != AccountType.CHECKING) {
            return Mono.error(new IllegalArgumentException("Holders/signatories are only allowed for CHECKING accounts"));
        }

        // Normalizaciones / defaults para evitar nulls
        if (domain.getBalance() == null) domain.setBalance(BigDecimal.ZERO);
        if (domain.getMonthlyMovements() == null) domain.setMonthlyMovements(0);
        if (domain.getActive() == null) domain.setActive(true);
        if (domain.getHolders() == null) domain.setHolders(new ArrayList<>());
        if (domain.getSignatories() == null) domain.setSignatories(new ArrayList<>());

        // aquí podrías llamar a ms_customer para validar customerId (si lo deseas)
        return accountRepository.create(domain);
    }

    public Mono<Account> update(Account domain) {
        if (domain.getId() == null) {
            return Mono.error(new IllegalArgumentException("Account id is required for update"));
        }

        return accountRepository.getById(domain.getId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Account not found")))
                .flatMap(existing -> {
                    // Actualizar campos permitidos (no sobreescribir movimientos históricos inesperadamente)
                    if (domain.getAccountNumber() != null) existing.setAccountNumber(domain.getAccountNumber());
                    if (domain.getAccountType() != null) existing.setAccountType(domain.getAccountType());
                    if (domain.getFixedDay() != null) existing.setFixedDay(domain.getFixedDay());
                    if (domain.getBalance() != null) existing.setBalance(domain.getBalance());
                    if (domain.getMonthlyMovements() != null) existing.setMonthlyMovements(domain.getMonthlyMovements());

                    // holders/signatories: validar coherencia con tipo de cuenta
                    if (domain.getHolders() != null) {
                        if (existing.getAccountType() != AccountType.CHECKING && domain.getHolders().size() > 0) {
                            return Mono.error(new IllegalArgumentException("Cannot add holders to a non-CHECKING account"));
                        }
                        existing.setHolders(domain.getHolders());
                    }
                    if (domain.getSignatories() != null) {
                        if (existing.getAccountType() != AccountType.CHECKING && domain.getSignatories().size() > 0) {
                            return Mono.error(new IllegalArgumentException("Cannot add signatories to a non-CHECKING account"));
                        }
                        existing.setSignatories(domain.getSignatories());
                    }

                    if (domain.getActive() != null) existing.setActive(domain.getActive());

                    // Revalidar reglas simples
                    if (existing.getAccountType() == AccountType.FIXED_TERM && existing.getFixedDay() == null) {
                        return Mono.error(new IllegalArgumentException("Fixed term accounts require fixedDay"));
                    }

                    return accountRepository.update(existing);
                });
    }

    public Mono<Void> deleteById(String id) {
        return accountRepository.deleteById(id);
    }

    // ---------------------------------------------------
    // Operaciones que usan la lógica del dominio (Account)
    // ---------------------------------------------------

    public Mono<Account> deposit(String accountId, BigDecimal amount) {
        LocalDate today = LocalDate.now();

        return accountRepository.getById(accountId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Account not found")))
                .flatMap(account -> {
                    account.deposit(amount, today);           // lógica viva en el domain
                    return accountRepository.update(account); // persistir cambios
                });
    }

    public Mono<Account> withdraw(String accountId, BigDecimal amount) {
        LocalDate today = LocalDate.now();

        return accountRepository.getById(accountId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Account not found")))
                .flatMap(account -> {
                    account.withdraw(amount, today);
                    return accountRepository.update(account);
                });
    }

    public Mono<Account> addHolder(String accountId, String holderId) {
        return accountRepository.getById(accountId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Account not found")))
                .flatMap(account -> {
                    if (account.getAccountType() != AccountType.CHECKING) {
                        return Mono.error(new IllegalArgumentException("Holders can only be added to CHECKING accounts"));
                    }
                    account.addHolder(holderId);
                    return accountRepository.update(account);
                });
    }

    public Mono<Account> addSignatory(String accountId, String signatoryId) {
        return accountRepository.getById(accountId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Account not found")))
                .flatMap(account -> {
                    if (account.getAccountType() != AccountType.CHECKING) {
                        return Mono.error(new IllegalArgumentException("Signatories can only be added to CHECKING accounts"));
                    }
                    account.addSignatory(signatoryId);
                    return accountRepository.update(account);
                });
    }

    // ---------------------------------------------------
    // Helpers privados del servicio
    // ---------------------------------------------------
    private boolean hasHoldersOrSignatories(Account domain) {
        return (domain.getHolders() != null && !domain.getHolders().isEmpty())
                || (domain.getSignatories() != null && !domain.getSignatories().isEmpty());
    }
}
