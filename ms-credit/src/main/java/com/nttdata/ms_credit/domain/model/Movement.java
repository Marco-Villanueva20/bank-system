package com.nttdata.ms_credit.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {

    private String id;
    private String creditId;
    private MovementType type;
    private BigDecimal amount;
    private LocalDateTime date;


}
