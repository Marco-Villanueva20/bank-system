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
        domain.setId(id); // 🔥 IMPORTANTE: asignar id al domain

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

    // -------------------------------------------------------
    // OPERACIONES ESPECIALES
    // -------------------------------------------------------

    // DEPOSITAR
    @PostMapping("/{id}/deposit")
    public Mono<AccountResponseDTO> deposit(
            @PathVariable String id,
            @RequestBody Map<String, BigDecimal> body) {

        BigDecimal amount = body.get("amount");
        return accountService.deposit(id, amount)
                .map(accountMapper::toResponseDTO);
    }

    // RETIRAR
    @PostMapping("/{id}/withdraw")
    public Mono<AccountResponseDTO> withdraw(
            @PathVariable String id,
            @RequestBody Map<String, BigDecimal> body) {

        BigDecimal amount = body.get("amount");
        return accountService.withdraw(id, amount)
                .map(accountMapper::toResponseDTO);
    }

    // AGREGAR HOLDER
    @PostMapping("/{id}/holders")
    public Mono<AccountResponseDTO> addHolder(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {

        String holderId = body.get("holderId");
        return accountService.addHolder(id, holderId)
                .map(accountMapper::toResponseDTO);
    }

    // AGREGAR SIGNATORY
    @PostMapping("/{id}/signatories")
    public Mono<AccountResponseDTO> addSignatory(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {

        String signatoryId = body.get("signatoryId");
        return accountService.addSignatory(id, signatoryId)
                .map(accountMapper::toResponseDTO);
    }
}
