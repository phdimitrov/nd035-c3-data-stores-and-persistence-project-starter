package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerService customerService;

    public Pet save(final Pet pet, final Long customerId) {
        final Customer customer = customerService.findById(customerId);
        pet.setCustomer(customer);
        final Pet savedPet = petRepository.save(pet);

        customer.addPet(savedPet);
        customerService.save(customer);

        return savedPet;
    }

    public Pet findById(final Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Missing or no such pet id"));
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public List<Pet> findAllById(List<Long> ids) {
        return petRepository.findAllById(ids);
    }

    public List<Pet> findByCustomerId(final Long customerId) {
        return petRepository.findByCustomerId(customerId);
    }

}
