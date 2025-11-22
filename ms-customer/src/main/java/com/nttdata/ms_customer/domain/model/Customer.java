package com.nttdata.ms_customer.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private String id;
    private String firstname;
    private String lastname;

    private String documentNumber;
    private String email;
    private String phone;
    private CustomerType customerType;

    private Boolean active;

    public void ensureActive() {
        if (active != null && !active) {
            throw new RuntimeException("Customer is inactive");
        }
    }
}
