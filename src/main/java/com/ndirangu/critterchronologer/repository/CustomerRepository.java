package com.ndirangu.critterchronologer.repository;

import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByPets(Pet pet);
}
