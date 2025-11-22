package com.nttdata.ms_account.domain.service;

import com.nttdata.ms_account.domain.exception.BusinessException;
import com.nttdata.ms_account.domain.model.Account;
import com.nttdata.ms_account.domain.model.AccountType;
import com.nttdata.ms_account.infrastructure.client.dto.CustomerResponseDTO;
import com.nttdata.ms_account.domain.repository.AccountRepository;
import com.nttdata.ms_account.infrastructure.client.CustomerClient;
import com.nttdata.ms_account.persistence.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerClient customerClient;

    public Flux<Account> getAll() {
        return accountRepository.getAll();
    }

    public Mono<Account> getById(String id) {
        return accountRepository.getById(id);
    }

    /**
     * create account verify type client
     */
    public Mono<Account> create(Account domain) {
        return customerClient.getCustomerById(domain.getCustomerId())
                .switchIfEmpty(Mono.error(new BusinessException("El cliente no existe")))
                .flatMap(customer -> switch (domain.getAccountType()) {
                    case SAVINGS -> createSavings(domain, customer);
                    case CHECKING -> createChecking(domain, customer);
                    case FIXED_TERM -> createFixedTerm(domain, customer);
                });
    }



    public Mono<Account> update(Account domain) {

        if (domain.getId() == null) {
            return Mono.error(new IllegalArgumentException("Account id is required for update"));
        }

        return accountRepository.getById(domain.getId())
                .switchIfEmpty(Mono.error(new BusinessException("Account not found")))
                .flatMap(existing -> {

                    // ====== CAMPOS NO EDITABLES (PROTECCIÓN) ======
                    if (domain.getAccountType() != null
                            && domain.getAccountType() != existing.getAccountType()) {
                        return Mono.error(new BusinessException("Account type cannot be changed"));
                    }

                    if (domain.getCustomerId() != null
                            && !domain.getCustomerId().equals(existing.getCustomerId())) {
                        return Mono.error(new BusinessException("Customer cannot be changed"));
                    }

                    if (domain.getAccountNumber() != null
                            && !domain.getAccountNumber().equals(existing.getAccountNumber())) {
                        return Mono.error(new BusinessException("Account number cannot be changed"));
                    }

                    if (domain.getBalance() != null
                            && domain.getBalance().compareTo(existing.getBalance()) != 0) {
                        return Mono.error(new BusinessException("Balance cannot be modified manually"));
                    }

                    if (domain.getMonthlyMovementCount() != null
                            && !domain.getMonthlyMovementCount().equals(existing.getMonthlyMovementCount())) {
                        return Mono.error(new BusinessException("Monthly movement count cannot be modified"));
                    }

                    // ====== CAMPOS EDITABLES ======

                    // Límite movimientos → editable
                    if (domain.getMonthlyMovementLimit() != null) {
                        existing.setMonthlyMovementLimit(domain.getMonthlyMovementLimit());
                    }

                    // Titulares y firmantes (solo CHECKING)
                    if (domain.getHolders() != null) {
                        if (existing.getAccountType() != AccountType.CHECKING) {
                            return Mono.error(new BusinessException("Only CHECKING accounts can have holders"));
                        }
                        existing.setHolders(domain.getHolders());
                    }

                    if (domain.getSigners() != null) {
                        if (existing.getAccountType() != AccountType.CHECKING) {
                            return Mono.error(new BusinessException("Only CHECKING accounts can have signers"));
                        }
                        existing.setSigners(domain.getSigners());
                    }

                    // fixedTermDay → SOLO FIXED TERM
                    if (domain.getFixedTermDay() != null) {
                        if (existing.getAccountType() != AccountType.FIXED_TERM) {
                            return Mono.error(new BusinessException("Only fixed-term accounts can have fixedTermDay"));
                        }
                        existing.setFixedTermDay(domain.getFixedTermDay());
                    }

                    // Active → editable
                    if (domain.getActive() != null) {
                        existing.setActive(domain.getActive());
                    }

                    return accountRepository.update(existing);
                });
    }


    public Mono<Void> deleteById(String id) {
        return accountRepository.deleteById(id);
    }




    /**Client Personal 1 account savings
     * */
    private Mono<Account> createSavings(Account domain, CustomerResponseDTO customer) {

        if ("BUSINESS".equals(customer.getCustomerType())) {
            return Mono.error(new BusinessException("Una empresa no puede tener cuenta de ahorro"));
        }

        return accountRepository.findByCustomerId(domain.getCustomerId())
                .collectList()
                .flatMap(accounts -> {

                    boolean existsSavings = accounts.stream()
                            .anyMatch(acc -> acc.getAccountType() == AccountType.SAVINGS);

                    if (existsSavings) {
                        return Mono.error(new BusinessException("El cliente ya tiene una cuenta de ahorro"));
                    }

                    return accountRepository.create(domain);
                });
    }
    /**Client Personal 1 account checking and Client Business n account checking
     * */
    private Mono<Account> createChecking(Account domain, CustomerResponseDTO customer) {

        // Las empresas sí pueden tener múltiples CHECKING → sin validación

        if ("PERSONAL".equals(customer.getCustomerType())) {

            return accountRepository.findByCustomerId(domain.getCustomerId())
                    .collectList()
                    .flatMap(accounts -> {

                        boolean existsChecking = accounts.stream()
                                .anyMatch(acc -> acc.getAccountType() == AccountType.CHECKING);

                        if (existsChecking) {
                            return Mono.error(new BusinessException("El cliente ya tiene una cuenta corriente"));
                        }

                        return accountRepository.create(domain);
                    });
        }
        if (domain.getSigners() == null || domain.getSigners().isEmpty()) {
            return Mono.error(new BusinessException(
                    "Debes de mencionar al menos un titular"
            ));
        }

        // Si es empresa, no se valida límite → se crea directamente
        return accountRepository.create(domain);
    }
    /**Client Personal 1 account fixed term
     * */
    private Mono<Account> createFixedTerm(Account domain, CustomerResponseDTO customer) {

        if ("BUSINESS".equals(customer.getCustomerType())) {
            return Mono.error(new BusinessException("Una empresa no puede tener cuentas a plazo fijo"));
        }

        return accountRepository.findByCustomerId(domain.getCustomerId())
                .collectList()
                .flatMap(accounts -> {

                    boolean existsFixed = accounts.stream()
                            .anyMatch(acc -> acc.getAccountType() == AccountType.FIXED_TERM);

                    if (existsFixed) {
                        return Mono.error(new BusinessException("El cliente ya tiene una cuenta a plazo fijo"));
                    }

                    return accountRepository.create(domain);
                });
    }




}
