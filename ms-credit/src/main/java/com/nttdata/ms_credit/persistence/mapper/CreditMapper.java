package com.nttdata.ms_credit.persistence.mapper;

import com.nttdata.ms_credit.domain.dto.CreditRequestDTO;
import com.nttdata.ms_credit.domain.dto.CreditResponseDto;
import com.nttdata.ms_credit.domain.model.Credit;
import com.nttdata.ms_credit.persistence.entity.CreditEntity;

public class CreditMapper {
    public Credit toDomain(CreditRequestDTO request) {
        return Credit.builder()
                .originalAmount(request.getOriginalAmount())
                .customerId(request.getCustomerId())
                .creditType(request.getCreditType())
                .build();
    }

    public CreditResponseDto toResponseDTO(Credit credit) {
        return CreditResponseDto.
                builder()
                .id(credit.getId())
                .creditType(credit.getCreditType())
                .creditLimit(credit.getCreditLimit())
                .active(credit.getActive())
                .consumedAmount(credit.getConsumedAmount())
                .createdAt(credit.getCreatedAt())
                .customerId(credit.getCustomerId())
                .originalAmount(credit.getOriginalAmount())
                .outstandingAmount(credit.getOutstandingAmount())
                .build();
    }

    public CreditEntity toEntity(Credit credit) {
        return CreditEntity.
                builder()
                .id(credit.getId())
                .creditType(credit.getCreditType())
                .creditLimit(credit.getCreditLimit())
                .active(credit.getActive())
                .consumedAmount(credit.getConsumedAmount())
                .createdAt(credit.getCreatedAt())
                .customerId(credit.getCustomerId())
                .originalAmount(credit.getOriginalAmount())
                .outstandingAmount(credit.getOutstandingAmount())
                .build();
    }

    public Credit toDomain(CreditEntity entity) {
        return Credit.
                builder()
                .id(entity.getId())
                .creditType(entity.getCreditType())
                .creditLimit(entity.getCreditLimit())
                .active(entity.getActive())
                .consumedAmount(entity.getConsumedAmount())
                .createdAt(entity.getCreatedAt())
                .customerId(entity.getCustomerId())
                .originalAmount(entity.getOriginalAmount())
                .outstandingAmount(entity.getOutstandingAmount())
                .build();
    }


}
