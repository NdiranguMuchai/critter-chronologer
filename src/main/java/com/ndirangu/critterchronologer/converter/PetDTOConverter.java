package com.ndirangu.critterchronologer.converter;

import com.ndirangu.critterchronologer.dto.PetDTO;
import com.ndirangu.critterchronologer.model.Pet;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


/**
 * Refactored convert methods here to make controller class cleaner.
 */

@Service
public class PetDTOConverter {

    public PetDTO convertPetToDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);

        petDTO.setOwnerId(pet.getOwner().getId());

        return petDTO;
    }

    public Pet convertDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        return pet;
    }
}
