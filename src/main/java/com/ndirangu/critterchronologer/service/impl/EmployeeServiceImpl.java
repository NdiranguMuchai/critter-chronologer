package com.ndirangu.critterchronologer.service.impl;

import com.ndirangu.critterchronologer.model.Employee;
import com.ndirangu.critterchronologer.model.EmployeeSkill;
import com.ndirangu.critterchronologer.repository.EmployeeRepository;
import com.ndirangu.critterchronologer.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Long employeeId) throws Exception {
        return employeeRepository.findById(employeeId).orElseThrow(()->
                new Exception("Employee with id "+employeeId+" not found"));
    }

    @Override
    public void setAvailability(Set<DayOfWeek> availability, Long employeeId) throws Exception{
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->
                new Exception("Employee with id "+employeeId+" not found"));

        employee.setDaysAvailable(availability);
    }

    @Override
    public Set<Employee> findEmployeesForService(LocalDate localDate, HashSet<EmployeeSkill> skills) {
        Set<Employee> employeesWithSkills = new HashSet<>();
        Set<Employee> availableEmployees = employeeRepository.findEmployeeByDaysAvailable(localDate.getDayOfWeek());

        availableEmployees.forEach(employee -> {
            boolean matchedSkillSet = employee.getSkills().containsAll(skills);

            if (matchedSkillSet) {
                employeesWithSkills.add(employee);
            }
        });

        return employeesWithSkills;
    }

    @Override
    public List<Employee> list() {
        return employeeRepository.findAll();
    }
}
