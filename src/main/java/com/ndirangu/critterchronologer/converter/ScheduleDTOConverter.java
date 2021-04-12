package com.ndirangu.critterchronologer.converter;

import com.ndirangu.critterchronologer.dto.ScheduleDTO;
import com.ndirangu.critterchronologer.model.Employee;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.model.Schedule;
import com.ndirangu.critterchronologer.service.EmployeeService;
import com.ndirangu.critterchronologer.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Refactored convert methods here to make controller class cleaner.
 */

@Service
public class ScheduleDTOConverter {
    private final PetService petService;
    private final EmployeeService employeeService;

    public ScheduleDTOConverter(PetService petService, EmployeeService employeeService) {
        this.petService = petService;
        this.employeeService = employeeService;
    }

    public ScheduleDTO convertScheduleToDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        if (schedule.getEmployees() != null){
            schedule.getEmployees().forEach(employee -> employeeIds.add(employee.getId()));
        }

        if (schedule.getPets() != null){
            schedule.getPets().forEach(pet -> petIds.add(pet.getId()));
        }

        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }

    public Schedule convertDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();

        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        if (employeeIds != null){
            employeeIds.forEach(employeeId -> {
                try {
                    employees.add(employeeService.findById(employeeId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if (petIds != null){
            petIds.forEach(petId -> {
                try {
                    pets.add(petService.findById(petId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return schedule;
    }
}
