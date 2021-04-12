package com.ndirangu.critterchronologer.converter;

import com.ndirangu.critterchronologer.dto.CustomerDTO;
import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Refactored convert methods here to make controller class cleaner.
 */

@Service
public class CustomerDTOConverter {
private final PetService petService;

    public CustomerDTOConverter(PetService petService) {
        this.petService = petService;
    }

    public CustomerDTO convertCustomerToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        List<Long> petIds = new ArrayList<>();

        if (customer.getPets() != null){
            customer.getPets().forEach(pet -> petIds.add(pet.getId()));
        }

        customerDTO.setPetIds(petIds);

        return customerDTO;
    }

    public Customer convertDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        List<Long> petIds = customerDTO.getPetIds();

        if (petIds != null){
            List<Pet> pets = petService.list();
            customer.setPets(pets);
        }

        return customer;
    }
}
