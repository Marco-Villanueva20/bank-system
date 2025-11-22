package com.nttdata.ms_account.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {
    private String id;
    private String firstname;
    private String lastname;
    private String documentNumber;
    private String email;
    private String phone;
    private String customerType;
    private Boolean active;
}
