package com.nttdata.ms_account.persistence.mapper;

import com.nttdata.ms_account.domain.dto.AccountRequestDTO;
import com.nttdata.ms_account.domain.dto.AccountResponseDTO;
import com.nttdata.ms_account.domain.model.Account;
import com.nttdata.ms_account.persistence.entity.AccountEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountMapper {

    public AccountEntity toEntity(Account domain){
        return AccountEntity.builder()
                .id(domain.getId())
                .accountNumber(domain.getAccountNumber())
                .customerId(domain.getCustomerId())
                .accountType(domain.getAccountType())
                .balance(domain.getBalance())
                .monthlyMovements(domain.getMonthlyMovements())
                .active(domain.getActive())
                .build();
    }

    public Account toDomain(AccountEntity entity){
        return Account.builder()
                .id(entity.getId())
                .accountNumber(entity.getAccountNumber())
                .customerId(entity.getCustomerId())
                .accountType(entity.getAccountType())
                .balance(entity.getBalance())
                .monthlyMovements(entity.getMonthlyMovements())
                .active(entity.getActive())
                .build();
    }

    public Account toDomain(AccountRequestDTO dto){
        return Account.builder()
                .customerId(dto.getCustomerId())
                .accountType(dto.getAccountType())
                .balance(BigDecimal.valueOf(dto.getInitialDeposit() == null ? 0.0 : dto.getInitialDeposit()))
                .monthlyMovements(0)
                .active(true)
                .build();
    }

    public AccountResponseDTO toResponseDTO(Account domain){
        return AccountResponseDTO.builder()
                .id(domain.getId())
                .accountNumber(domain.getAccountNumber())
                .customerId(domain.getCustomerId())
                .accountType(domain.getAccountType())
                .balance(domain.getBalance())
                .monthlyMovements(domain.getMonthlyMovements())
                .active(domain.getActive())
                .build();
    }
}
