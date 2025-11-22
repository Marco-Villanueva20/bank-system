package com.nttdata.ms_credit.web.controller;

import com.nttdata.ms_credit.domain.dto.MovementRequestDTO;
import com.nttdata.ms_credit.domain.dto.MovementResponseDTO;
import com.nttdata.ms_credit.domain.model.Movement;
import com.nttdata.ms_credit.domain.service.MovementService;
import com.nttdata.ms_credit.persistence.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/credit-movements")
@RequiredArgsConstructor
public class MovementController {
    private final MovementMapper movementMapper;
    private final MovementService movementService;


    @GetMapping
    public ResponseEntity<Flux<MovementResponseDTO>> findAll(){
        return ResponseEntity.ok(movementService.findAll().map(movementMapper::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<MovementResponseDTO>> findById(@PathVariable String id){
        return ResponseEntity.ok(movementService.findById(id).map(movementMapper::toResponse));
    }
    @PostMapping
    public ResponseEntity<Mono<Movement>> create(MovementRequestDTO request){
        return ResponseEntity.ok(movementService.create(movementMapper.toDomain(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<Movement>> update(@PathVariable String id, @RequestBody MovementRequestDTO request){
        Movement movement = movementMapper.toDomain(request);
        movement.setId(id);
        return ResponseEntity.ok(movementService.update(movementMapper.toDomain(request)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteById(@PathVariable String id){
        return ResponseEntity.ok(movementService.deleteById(id));
    }
}
