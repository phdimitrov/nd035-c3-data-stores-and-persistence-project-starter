package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(final Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findById(final Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Missing or no such employee id"));
    }

    public List<Employee> findAllById(List<Long> ids) {
        return employeeRepository.findAllById(ids);
    }

    public Employee setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        final Employee employee = findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        return save(employee);
    }

    public List<Employee> findAvailable(Set<EmployeeSkill> skills, Set<DayOfWeek> dayOfWeeks) {
        //this query will return all matching any skill and any day
        List<Employee> employees = employeeRepository.findDistinctBySkillsInAndDaysAvailableIn(skills, dayOfWeeks);

        //filter to get only those that have all requested skills for those days
        return employees.stream()
                .filter(employee ->
                        employee.getDaysAvailable().containsAll(dayOfWeeks) && employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}
