package com.nttdata.ms_account.domain.model;

import com.nttdata.ms_account.domain.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private static final int SAVINGS_MOVEMENT_LIMIT = 5;

    private String id;
    private String accountNumber;
    private String customerId;

    private AccountType accountType;
    private BigDecimal balance; //SALDO
    private List<String> holders; //titulares
    private List<String> signers; //firmantes
    private Integer fixedTermDay; //Dia permitido para FIXED_TERM
    private Integer monthlyMovementCount;      // contador actual
    private Integer monthlyMovementLimit;      // límite configurable
    private LocalDateTime createdAt; //creado
    private Boolean active;

    // =============================================================
    //                       MÉTODOS DE DOMINIO
    // =============================================================


    public void validateSavingsMovementAllowed() {
        if (monthlyMovementCount >= SAVINGS_MOVEMENT_LIMIT) {
            throw new RuntimeException("Superó el límite mensual de movimientos para Ahorro");
        }
    }


    public void validateFixedTermMovementAllowed() {
        if (monthlyMovementCount >= 1) {
            throw new RuntimeException("Solo se permite un movimiento mensual en Plazo Fijo");
        }
    }
    public void requireEnoughBalance(BigDecimal amount){
        if (this.balance.compareTo(amount) < 0){
            throw new BusinessException("Insufficient balance");
        }
    }


}
