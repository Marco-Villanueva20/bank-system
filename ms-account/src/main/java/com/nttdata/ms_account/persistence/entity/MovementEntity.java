package com.nttdata.ms_account.persistence.entity;

import com.nttdata.ms_account.domain.model.MovementType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("credit_movements")
public class MovementEntity {
    @Id
    private String id;
    private String accountId;
    private MovementType type; //DEPOSIT, WITHDRAWAL
    private BigDecimal amount; //Cantidad
    private LocalDateTime date;
    private String description;
}
