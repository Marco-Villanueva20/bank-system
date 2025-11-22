package com.nttdata.ms_credit.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credit {
    private String id;

    private String customerId;
    private CreditType creditType;  // PERSONAL, BUSINESS, CREDIT_CARD

    private BigDecimal originalAmount;    // Monto del préstamo
    private BigDecimal outstandingAmount; // Lo que falta pagar

    private BigDecimal creditLimit;       // Límite total
    private BigDecimal consumedAmount;    // Monto consumido (carritos, restaurantes, etc)

    private Boolean active;
    private LocalDateTime createdAt;
}
