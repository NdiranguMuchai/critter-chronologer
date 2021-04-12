package com.ndirangu.critterchronologer.controller;

import com.ndirangu.critterchronologer.converter.ScheduleDTOConverter;
import com.ndirangu.critterchronologer.dto.ScheduleDTO;
import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Employee;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.model.Schedule;
import com.ndirangu.critterchronologer.service.CustomerService;
import com.ndirangu.critterchronologer.service.EmployeeService;
import com.ndirangu.critterchronologer.service.PetService;
import com.ndirangu.critterchronologer.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
@Api(tags = {"Schedule"})
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final PetService petService;
    private final ScheduleDTOConverter scheduleDTOConverter;

    public ScheduleController(ScheduleService scheduleService,
                              CustomerService customerService,
                              EmployeeService employeeService,
                              PetService petService,
                              ScheduleDTOConverter scheduleDTOConverter) {

        this.scheduleService = scheduleService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
        this.scheduleDTOConverter = scheduleDTOConverter;
    }

    @PostMapping
    @ApiOperation(value = "Creates a schedule object")
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTOConverter.convertDTOToSchedule(scheduleDTO);
        return scheduleDTOConverter.convertScheduleToDTO(scheduleService.create(schedule));

    }

    @GetMapping
    @ApiOperation(value = "Returns a list of all schedules")
    public List<ScheduleDTO> getAllSchedules() {

        return scheduleService.list()
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    @ApiOperation(value = "Returns a list of all schedules for a pet given the pet id")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) throws Exception{
        Pet pet = petService.findById(petId);

        return scheduleService.findScheduleByPet(pet)
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    @ApiOperation(value = "Returns a list of all schedules for an employee given the employee id")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) throws Exception{
        Employee employee = employeeService.findById(employeeId);

        return scheduleService.findScheduleByEmployee(employee)
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    @ApiOperation(value = "Returns a list of all schedules for a customer given the customer id")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.findById(customerId);

        return scheduleService.findScheduleByCustomer(customer)
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

}
