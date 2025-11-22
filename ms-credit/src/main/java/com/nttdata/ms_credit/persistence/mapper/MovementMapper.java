package com.nttdata.ms_credit.persistence.mapper;

import com.nttdata.ms_credit.domain.dto.MovementRequestDTO;
import com.nttdata.ms_credit.domain.dto.MovementResponseDTO;
import com.nttdata.ms_credit.domain.model.Movement;
import com.nttdata.ms_credit.persistence.entity.MovementEntity;
import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementMapper {


    public MovementResponseDTO toResponse(Movement domain){
        return MovementResponseDTO.builder()
                .id(domain.getId())
                .amount(domain.getAmount())
                .creditId(domain.getCreditId())
                .date(domain.getDate())
                .type(domain.getType())
                .build();
    }

    public Movement toDomain(MovementEntity entity){
       return Movement.builder()
                .id(entity.getId())
                .type(entity.getType())
                .date(entity.getDate())
                .amount(entity.getAmount())
                .creditId(entity.getCreditId())
                .build();
    }

    public Movement toDomain(MovementRequestDTO requestDTO){
        return Movement.builder()
                .type(requestDTO.getType())
                .date(requestDTO.getDate())
                .amount(requestDTO.getAmount())
                .creditId(requestDTO.getCreditId())
                .build();
    }

    public MovementEntity toEntity(Movement dominio) {
        return MovementEntity.builder()
                .id(dominio.getId())
                .type(dominio.getType())
                .date(dominio.getDate())
                .amount(dominio.getAmount())
                .creditId(dominio.getCreditId())
                .build();
    }
}
