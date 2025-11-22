package com.nttdata.ms_account.web.controller;

import com.nttdata.ms_account.domain.dto.AccountRequestDTO;
import com.nttdata.ms_account.domain.dto.AccountResponseDTO;
import com.nttdata.ms_account.domain.model.Account;
import com.nttdata.ms_account.domain.service.AccountService;
import com.nttdata.ms_account.persistence.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    // -------------------------------------------------------
    // GET ALL
    // -------------------------------------------------------
    @GetMapping
    public Flux<AccountResponseDTO> getAll() {
        return accountService.getAll()
                .map(accountMapper::toResponseDTO);
    }

    // -------------------------------------------------------
    // GET BY ID
    // -------------------------------------------------------
    @GetMapping("/{id}")
    public Mono<AccountResponseDTO> getById(@PathVariable String id) {
        return accountService.getById(id)
                .map(accountMapper::toResponseDTO);
    }

    // -------------------------------------------------------
    // CREATE
    // -------------------------------------------------------
    @PostMapping
    public Mono<AccountResponseDTO> create(@RequestBody AccountRequestDTO requestDTO) {
        Account account = accountMapper.toDomain(requestDTO);
        return accountService.create(account)
                .map(accountMapper::toResponseDTO);
    }

    // -------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------
    @PutMapping("/{id}")
    public Mono<AccountResponseDTO> update(
            @PathVariable String id,
            @RequestBody AccountRequestDTO requestDTO) {

        Account domain = accountMapper.toDomain(requestDTO);
        domain.setId(id);

        return accountService.update(domain)
                .map(accountMapper::toResponseDTO);
    }

    // -------------------------------------------------------
    // DELETE
    // -------------------------------------------------------
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return accountService.deleteById(id);
    }

}
