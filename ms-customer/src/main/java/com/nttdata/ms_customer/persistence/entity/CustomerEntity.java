package com.nttdata.ms_customer.persistence.entity;

import com.nttdata.ms_customer.domain.model.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
@Builder
public class CustomerEntity {
    @Id
    private String id;
    private String firstname;
    private String lastname;

    @Indexed(unique = true)
    private String documentNumber;
    private String email;
    private String phone;
    private CustomerType customerType;
    private Boolean active;
}


