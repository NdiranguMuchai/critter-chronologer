package com.ndirangu.critterchronologer.service.impl;

import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Employee;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.model.Schedule;
import com.ndirangu.critterchronologer.repository.ScheduleRepository;
import com.ndirangu.critterchronologer.service.ScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule create(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> list() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> findScheduleByPet(Pet pet) {
        return scheduleRepository.findScheduleByPets(pet);
    }

    @Override
    public List<Schedule> findScheduleByEmployee(Employee employee) {
        return scheduleRepository.findScheduleByEmployees(employee);
    }

    @Override
    public List<Schedule> findScheduleByCustomer(Customer customer) {
        List<Pet> pets = customer.getPets();
        List<Schedule> schedules = new LinkedList<>();

        pets.forEach(pet -> {
            List<Schedule> petsOnSchedule = scheduleRepository.findScheduleByPets(pet);
            schedules.addAll(petsOnSchedule);
        });

        return schedules;
    }
}
