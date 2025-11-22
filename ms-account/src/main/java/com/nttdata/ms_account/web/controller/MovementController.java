package com.nttdata.ms_account.web.controller;

import com.nttdata.ms_account.domain.dto.MovementRequestDTO;
import com.nttdata.ms_account.domain.dto.MovementResponseDTO;
import com.nttdata.ms_account.domain.service.MovementService;
import com.nttdata.ms_account.persistence.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementMapper movementMapper;
    private final MovementService movementService;
    @PostMapping
    public ResponseEntity<Mono<MovementResponseDTO>> deposit(@RequestBody MovementRequestDTO request){
        return ResponseEntity.ok(movementService.deposit(movementMapper.toDomain(request)).map(movementMapper::toResponseDto
        ));
    }

    @PostMapping
    public ResponseEntity<Mono<MovementResponseDTO>> withdraw(@RequestBody MovementRequestDTO request){
        return ResponseEntity.ok(movementService.withdraw(movementMapper.toDomain(request)).map(movementMapper::toResponseDto
        ));
    }
}
