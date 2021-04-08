package com.ndirangu.critterchronologer.service;

import com.ndirangu.critterchronologer.model.Pet;

import java.util.List;

public interface PetService {
    Pet save(Pet pet);
    List<Pet> list();
    Pet findById(Long id) throws Exception;
    List<Pet> findByOwnerId(Long ownerId);
}
