package com.ndirangu.critterchronologer.service;

import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Pet;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    List<Customer> list();
    Customer findOwnerByPet(Pet pet) throws Exception;
    Customer findById(Long id);
}
