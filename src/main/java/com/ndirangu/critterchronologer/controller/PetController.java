package com.ndirangu.critterchronologer.controller;

import com.ndirangu.critterchronologer.converter.PetDTOConverter;
import com.ndirangu.critterchronologer.dto.PetDTO;
import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.service.CustomerService;
import com.ndirangu.critterchronologer.service.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pet")
@Api(tags = {"Pet"})
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class PetController {
    private final PetService petService;
    private final CustomerService customerService;
    private final PetDTOConverter petDTOConverter;

    public PetController(PetService petService, CustomerService customerService, PetDTOConverter petDTOConverter) {
        this.petService = petService;
        this.customerService = customerService;
        this.petDTOConverter = petDTOConverter;
    }

    @PostMapping
    @ApiOperation(value = "Creates a pet object")
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer owner = customerService.findById(petDTO.getOwnerId());

        Pet petToSave = petDTOConverter.convertDTOToPet(petDTO);
        petToSave.setOwner(owner);

        return petDTOConverter.convertPetToDTO(petService.save(petToSave));
    }

    @GetMapping("/{petId}")
    @ApiOperation(value = "Finds a pet object given its id")
    public PetDTO getPet(@PathVariable long petId) throws Exception{
        Pet pet = petService.findById(petId);
        return petDTOConverter.convertPetToDTO(pet);
    }


    @GetMapping
    @ApiOperation(value = "Returns a list of all pets ")
    public List<PetDTO> getPets(){
        return petService.list()
                .stream()
                .map(petDTOConverter::convertPetToDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/owner/{ownerId}")
    @ApiOperation(value = "Returns a list of all pets belonging to an owner given the owner id")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        return petService.findByOwnerId(ownerId)
                .stream()
                .map(petDTOConverter::convertPetToDTO)
                .collect(Collectors.toList());
    }


}
