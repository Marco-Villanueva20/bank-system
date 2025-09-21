package com.bank.system.account.model;



import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private Long id;
    private CustomerType type; // PERSONAL o BUSINESS //EMPRESARIAL
    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("company_name")
    private String companyName;

    @Column("document_number")
    private String documentNumber;

    private String email;
    private String phone;
}
