package com.nttdata.ms_account.domain.dto;

import com.nttdata.ms_account.domain.model.MovementType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class MovementResponseDTO {

    private String id;
    private String accountId;
    private MovementType type;
    private BigDecimal amount;
    private LocalDateTime date;
    private String description;

}
