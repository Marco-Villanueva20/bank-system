package com.nttdata.ms_account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movement {

    private String id;
    private String accountId;
    private MovementType type;
    private BigDecimal amount;
    private LocalDateTime date;
    private String description;



    /**
     * Verifica que el monto o cantidad ingresada sea mayor a cero
     */
    public void requirePositive() {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }
    }


}
