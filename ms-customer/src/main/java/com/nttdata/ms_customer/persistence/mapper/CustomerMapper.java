package com.nttdata.ms_customer.persistence.mapper;

import com.nttdata.ms_customer.domain.dto.CustomerRequestDTO;
import com.nttdata.ms_customer.domain.dto.CustomerResponseDTO;
import com.nttdata.ms_customer.domain.model.Customer;
import com.nttdata.ms_customer.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    //Controller to Service
    public Customer toDomain(CustomerResponseDTO customerResponseDTO){
        return Customer.builder()
                .id(customerResponseDTO.getId())
                .firstname(customerResponseDTO.getFirstname())
                .lastname(customerResponseDTO.getLastname())
                .documentNumber(customerResponseDTO.getDocumentNumber())
                .email(customerResponseDTO.getEmail())
                .phone(customerResponseDTO.getPhone())
                .customerType(customerResponseDTO.getCustomerType())
                .active(customerResponseDTO.getActive())
                .build();
    }

    //Service to Repository
    public CustomerEntity toEntity(Customer domain){
        return CustomerEntity.builder()
                .id(domain.getId())
                .firstname(domain.getFirstname())
                .lastname(domain.getLastname())
                .documentNumber(domain.getDocumentNumber())
                .email(domain.getEmail())
                .phone(domain.getPhone())
                .customerType(domain.getCustomerType())
                .active(domain.getActive())
                .build();
    }


    //Repository to Service
    public Customer toDomain(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .documentNumber(entity.getDocumentNumber())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .customerType(entity.getCustomerType())
                .active(entity.getActive())
                .build();
    }

    //Service to Controller
    public CustomerResponseDTO toResponseDto(Customer domain){
       return CustomerResponseDTO.builder()
                .id(domain.getId())
                .firstname(domain.getFirstname())
                .lastname(domain.getLastname())
                .documentNumber(domain.getDocumentNumber())
                .email(domain.getEmail())
                .phone(domain.getPhone())
                .customerType(domain.getCustomerType())
               .active(domain.getActive())
                .build();
    }

    public Customer toDomain(CustomerRequestDTO requestDTO){
        return  Customer.builder()
                .firstname(requestDTO.getFirstname())
                .lastname(requestDTO.getLastname())
                .documentNumber(requestDTO.getDocumentNumber())
                .email(requestDTO.getEmail())
                .phone(requestDTO.getPhone())
                .customerType(requestDTO.getCustomerType())
                .active(requestDTO.getActive())
                .build();
    }



}
