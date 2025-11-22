package com.nttdata.ms_account.persistence.mapper;


import com.nttdata.ms_account.domain.dto.MovementRequestDTO;
import com.nttdata.ms_account.domain.dto.MovementResponseDTO;
import com.nttdata.ms_account.domain.model.Movement;
import com.nttdata.ms_account.persistence.entity.MovementEntity;

public class MovementMapper {

    public Movement toDomain(MovementRequestDTO request){
        return  Movement.builder()
                .accountId(request.getAccountId())
                .type(request.getType())
                .amount(request.getAmount())
                .build();
    }

    public Movement toDomain(MovementEntity entity){
        return  Movement.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .description(entity.getDescription())
                .accountId(entity.getAccountId())
                .type(entity.getType())
                .amount(entity.getAmount())
                .build();
    }


    public MovementResponseDTO toResponseDto(Movement domain){
        return  MovementResponseDTO.builder()
                .id(domain.getId())
                .date(domain.getDate())
                .description(domain.getDescription())
                .accountId(domain.getAccountId())
                .type(domain.getType())
                .amount(domain.getAmount())
                .build();
    }

    public MovementEntity toEntity(Movement domain){
        return  MovementEntity.builder()
                .id(domain.getId())
                .date(domain.getDate())
                .description(domain.getDescription())
                .accountId(domain.getAccountId())
                .type(domain.getType())
                .amount(domain.getAmount())
                .build();
    }

}
