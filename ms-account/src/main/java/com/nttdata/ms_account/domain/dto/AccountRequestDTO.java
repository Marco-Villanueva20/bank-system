package com.nttdata.ms_account.domain.dto;


import com.nttdata.ms_account.domain.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDTO {
    private String customerId;
    private AccountType accountType;
    private Double initialDeposit; // opcional, 0 si no
    private Integer fixedDay;
}

