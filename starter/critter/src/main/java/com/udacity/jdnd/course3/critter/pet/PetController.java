package com.udacity.jdnd.course3.critter.pet;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.save(PetDTO.toEntity(petDTO), petDTO.getOwnerId());
        return PetDTO.toDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        final Pet pet = petService.findById(petId);
        return PetDTO.toDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        final List<Pet> pets = petService.findAll();
        return pets.stream()
                .map(PetDTO::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        final List<Pet> pets = petService.findByCustomerId(ownerId);
        return pets.stream()
                .map(PetDTO::toDTO)
                .collect(Collectors.toList());
    }
}
