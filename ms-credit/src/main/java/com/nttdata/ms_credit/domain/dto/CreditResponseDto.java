package com.nttdata.ms_credit.domain.dto;

import com.nttdata.ms_credit.domain.model.CreditType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class CreditResponseDto {
    private String id;

    private String customerId;
    private CreditType creditType;
    private BigDecimal originalAmount;
    private BigDecimal outstandingAmount;

    private BigDecimal creditLimit;
    private BigDecimal consumedAmount;

    private Boolean active;
    private LocalDateTime createdAt;
}
