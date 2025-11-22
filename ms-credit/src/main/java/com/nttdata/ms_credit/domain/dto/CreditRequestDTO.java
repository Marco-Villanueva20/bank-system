package com.nttdata.ms_credit.domain.dto;

import com.nttdata.ms_credit.domain.model.CreditType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class CreditRequestDTO {
    private String customerId;
    private CreditType creditType;
    private BigDecimal originalAmount;
}
