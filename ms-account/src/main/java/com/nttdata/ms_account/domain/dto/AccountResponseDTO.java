package com.nttdata.ms_account.domain.dto;

import com.nttdata.ms_account.domain.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private String id;
    private String accountNumber;
    private String customerId;
    private AccountType accountType;
    private BigDecimal balance;
    private Integer monthlyMovements;
    private Boolean active;
}
