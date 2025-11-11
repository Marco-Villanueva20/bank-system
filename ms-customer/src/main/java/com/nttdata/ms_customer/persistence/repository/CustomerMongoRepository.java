package com.nttdata.ms_customer.persistence.repository;

import com.nttdata.ms_customer.persistence.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMongoRepository extends ReactiveMongoRepository<CustomerEntity, String> {

}
