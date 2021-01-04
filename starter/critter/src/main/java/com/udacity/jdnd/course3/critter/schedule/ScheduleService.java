package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Schedule save(final Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findByPetId(final Long petId) {
        return petRepository.findById(petId)
                .map(pet -> scheduleRepository.findAllByPets(pet))
                .orElseThrow(() -> new IllegalArgumentException("Missing or no such petId"));
    }

    public List<Schedule> findByEmployeeId(final Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employee -> scheduleRepository.findAllByEmployees(employee))
                .orElseThrow(() -> new IllegalArgumentException("Missing or no such employeeId"));
    }

    public List<Schedule> findByCustomerId(final Long customerId) {
        return customerRepository.findById(customerId)
                .map(customer -> scheduleRepository.findAllByPetsIn(customer.getPets()))
                .orElseThrow(() -> new IllegalArgumentException("Missing or no such customerId"));
    }
}
