package com.nttdata.ms_account.infrastructure.client;

import com.nttdata.ms_account.infrastructure.client.dto.CustomerResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CustomerClient {
    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/api/v1/customers")
            .build();
    public Mono<CustomerResponseDTO> getCustomerById(String id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(CustomerResponseDTO.class);
    }
}
