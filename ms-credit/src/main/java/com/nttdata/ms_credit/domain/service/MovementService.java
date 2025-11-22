package com.nttdata.ms_credit.domain.service;

import com.nttdata.ms_credit.domain.model.Movement;
import com.nttdata.ms_credit.domain.repository.MovementRepository;
import com.nttdata.ms_credit.persistence.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovementService {
    private final MovementMapper movementMapper;
    private final MovementRepository movementRepository;

    public Flux<Movement> findAll(){
       return movementRepository.findAll();
    }
    public Mono<Movement> findById(String id){
        return movementRepository.findById(id);
    }
    public Mono<Movement> create(Movement movement){
        return movementRepository.create(movement);
    }
    public Mono<Movement> update(Movement movement){
        return movementRepository.update(movement);
    }
    public Mono<Void> deleteById(String id){
        return movementRepository.deleteById(id);
    }



}
