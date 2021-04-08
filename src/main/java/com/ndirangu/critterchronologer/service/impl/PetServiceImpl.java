package com.ndirangu.critterchronologer.service.impl;

import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.repository.CustomerRepository;
import com.ndirangu.critterchronologer.repository.PetRepository;
import com.ndirangu.critterchronologer.service.PetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetServiceImpl(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Pet save(Pet pet) {
        Pet petTOSave = petRepository.save(pet);
        Customer owner = petTOSave.getOwner();
        owner.addPet(petTOSave);
        customerRepository.save(owner);
        return  petTOSave;
    }

    @Override
    public List<Pet> list() {
        return petRepository.findAll();
    }

    @Override
    public Pet findById(Long id) throws Exception{
        return petRepository.findById(id).orElseThrow(()-> new Exception("Pet with id "+id+" not found"));
    }

    @Override
    public List<Pet> findByOwnerId(Long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);

    }
}
