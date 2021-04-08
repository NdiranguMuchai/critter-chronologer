package com.ndirangu.critterchronologer.controller;

import com.ndirangu.critterchronologer.dto.ScheduleDTO;
import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Employee;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.model.Schedule;
import com.ndirangu.critterchronologer.service.CustomerService;
import com.ndirangu.critterchronologer.service.EmployeeService;
import com.ndirangu.critterchronologer.service.PetService;
import com.ndirangu.critterchronologer.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final PetService petService;

    public ScheduleController(ScheduleService scheduleService,
                              CustomerService customerService,
                              EmployeeService employeeService,
                              PetService petService) {

        this.scheduleService = scheduleService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertDTOToSchedule(scheduleDTO);
        return convertScheduleToDTO(scheduleService.create(schedule));

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        return scheduleService.list()
                .stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) throws Exception{
        Pet pet = petService.findById(petId);

        return scheduleService.findScheduleByPet(pet)
                .stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) throws Exception{
        Employee employee = employeeService.findById(employeeId);

        return scheduleService.findScheduleByEmployee(employee)
                .stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.findById(customerId);

        return scheduleService.findScheduleByCustomer(customer)
                .stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }



    private ScheduleDTO convertScheduleToDTO(Schedule schedule){
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

    private Schedule convertDTOToSchedule(ScheduleDTO scheduleDTO){
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
