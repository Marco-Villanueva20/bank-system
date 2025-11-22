package com.nttdata.ms_credit.persistence.entity;

import com.nttdata.ms_credit.domain.model.CreditType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("credits")
public class CreditEntity {

    @Id
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
