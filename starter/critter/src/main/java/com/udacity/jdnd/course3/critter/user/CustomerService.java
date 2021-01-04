package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetService petService;

    public Customer save(final Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findById(final Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Missing or no such customer id"));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findByPetId(final Long petId) {
        Pet pet = petService.findById(petId);
        return customerRepository.findByPets(pet)
                .orElseThrow(() -> new IllegalArgumentException("Missing or no such customer Id"));
    }
}
