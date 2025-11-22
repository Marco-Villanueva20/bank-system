package com.nttdata.ms_account.persistence.entity;

import com.nttdata.ms_account.domain.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "accounts")
public class AccountEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String accountNumber; // número único de cuenta
    private String customerId;
    private AccountType accountType;
    private BigDecimal balance; //SALDO
    private List<String> holders; //TITULARES
    private List<String> signers; //IDs de personas (firmantes)
    private Integer fixedTermDay; //Dia permitido para FIXED_TERM
    private Integer maxMonthlyMovements; //movimientos mensuales maximos
    private LocalDateTime createdAt; //creado


}
