package com.ndirangu.critterchronologer.repository;

import com.ndirangu.critterchronologer.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByOwnerId(Long ownerId);
}
