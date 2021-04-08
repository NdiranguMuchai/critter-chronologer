package com.ndirangu.critterchronologer.service.impl;

import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.repository.CustomerRepository;
import com.ndirangu.critterchronologer.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> list() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findOwnerByPet(Pet pet) {
        return customerRepository.findCustomerByPets(pet);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.getOne(id);
    }
}
