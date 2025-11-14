package com.nttdata.ms_account.domain.validator;

import com.nttdata.ms_account.domain.dto.AccountRequestDTO;
import com.nttdata.ms_account.domain.dto.CustomerResponseDTO;
import com.nttdata.ms_account.domain.model.AccountType;

public class AccountBusinessValidator {
    public void validateCreateRequest(AccountRequestDTO request, CustomerResponseDTO customer, Long currentCountOfType){
        AccountType type = request.getAccountType();

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        if(customer.getCustomerType().toString().equalsIgnoreCase("PERSONAL")){
            if (currentCountOfType != null && currentCountOfType>0){
                throw new RuntimeException("Personal customer already has an account of this type");
            }
            if (type == AccountType.FIXED_TERM && request.getFixedDay() == null) {
                throw new RuntimeException("Fixed term account requires fixedDay");
            }

        }else if ("ENTERPRISE".equalsIgnoreCase(customer.getCustomerType().toString())) {
            if (type == AccountType.SAVINGS || type == AccountType.FIXED_TERM) {
                throw new RuntimeException("Enterprise customers cannot have savings or fixed-term accounts");
            }
        }

        if (request.getInitialDeposit() == null || request.getInitialDeposit() < 0) {
            throw new RuntimeException("Initial deposit must be >= 0");
        }
    }
}
