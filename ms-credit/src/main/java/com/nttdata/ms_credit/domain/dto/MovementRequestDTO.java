package com.nttdata.ms_credit.domain.dto;

import com.nttdata.ms_credit.domain.model.MovementType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovementRequestDTO {
    private String creditId;
    private MovementType type;
    private BigDecimal amount;
    private LocalDateTime date;
}
