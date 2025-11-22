package com.nttdata.ms_account.domain.dto;

import com.nttdata.ms_account.domain.model.MovementType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@Builder
public class MovementRequestDTO {

    private String accountId;
    private MovementType type;
    private BigDecimal amount;

}
