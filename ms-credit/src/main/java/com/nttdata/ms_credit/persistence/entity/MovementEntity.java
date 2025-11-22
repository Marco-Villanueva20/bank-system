package com.nttdata.ms_credit.persistence.entity;

import com.nttdata.ms_credit.domain.model.MovementType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("credit_movements")
public class MovementEntity {
    @Id
    private String id;
    private String creditId;
    private MovementType type;
    private BigDecimal amount;
    private LocalDateTime date;
}
