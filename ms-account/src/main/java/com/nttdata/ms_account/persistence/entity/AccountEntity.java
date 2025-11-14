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
    private Integer fixedDay;
    private BigDecimal balance;
    private Integer monthlyMovements;
    private Boolean active;
}
