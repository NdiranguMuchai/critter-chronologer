package com.ndirangu.critterchronologer.controller;

import com.ndirangu.critterchronologer.dto.PetDTO;
import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.service.CustomerService;
import com.ndirangu.critterchronologer.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;
    private final CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer owner = customerService.findById(petDTO.getOwnerId());

        Pet petToSave = convertDTOToPet(petDTO);
        petToSave.setOwner(owner);

        return convertPetToDTO(petService.save(petToSave));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) throws Exception{
        Pet pet = petService.findById(petId);
        return convertPetToDTO(pet);
    }


    @GetMapping
    public List<PetDTO> getPets(){
        return petService.list()
                .stream()
                .map(this::convertPetToDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        return petService.findByOwnerId(ownerId)
                .stream()
                .map(this::convertPetToDTO)
                .collect(Collectors.toList());
    }


    private PetDTO convertPetToDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);

        petDTO.setOwnerId(pet.getOwner().getId());

        return petDTO;
    }

    private Pet convertDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        return pet;
    }

}
