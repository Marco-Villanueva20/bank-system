package com.nttdata.ms_credit.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceResponseDTO {
    private String productId;
    private BigDecimal availableBalance;
}
