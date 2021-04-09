package com.ndirangu.critterchronologer.controller;

import com.ndirangu.critterchronologer.dto.CustomerDTO;
import com.ndirangu.critterchronologer.dto.EmployeeDTO;
import com.ndirangu.critterchronologer.dto.EmployeeRequestDTO;
import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Employee;
import com.ndirangu.critterchronologer.model.EmployeeSkill;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.service.CustomerService;
import com.ndirangu.critterchronologer.service.EmployeeService;
import com.ndirangu.critterchronologer.service.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles requests for both customers and employees
 *
 * May split into separate controllers for each along the road
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"User"})
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class UserController {
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final PetService petService;

    public UserController(CustomerService customerService,
                          EmployeeService employeeService,
                          PetService petService) {

        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    @ApiOperation(value = "Creates a customer object")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertDTOToCustomer(customerDTO);
        return convertCustomerToDTO(customerService.save(customer));
    }

    @GetMapping("/customer")
    @ApiOperation(value = "Returns a list of all customers")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.list()
                .stream()
                .map(this::convertCustomerToDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/customer/pet/{petId}")
    @ApiOperation(value = "Finds a customer that owns a particular pet given the pet id")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) throws Exception{
        Pet pet = petService.findById(petId);

        Customer owner = customerService.findOwnerByPet(pet);

        return convertCustomerToDTO(owner);
    }


    @PostMapping("/employee")
    @ApiOperation(value = "Creates an employee object")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertDTOToEmployee(employeeDTO);
        return convertEmployeeToDTO(employeeService.save(employee));

    }

    @GetMapping("/employee/{employeeId}")
    @ApiOperation(value = "Finds an employee given the employee id")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) throws Exception{
        return convertEmployeeToDTO(employeeService.findById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    @ApiOperation(value = "Sets the days of the week that an employee is available")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable,
                                @PathVariable long employeeId) throws Exception{

        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    @ApiOperation(value = "Returns a list of employees who are available in a particular day")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        LocalDate localDate = employeeDTO.getDate();
        HashSet<EmployeeSkill> skills = new HashSet<>(employeeDTO.getSkills());

        return employeeService.findEmployeesForService(localDate, skills)
                .stream()
                .map(this::convertEmployeeToDTO)
                .collect(Collectors.toList());
    }

    private CustomerDTO convertCustomerToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        List<Long> petIds = new ArrayList<>();

        if (customer.getPets() != null){
            customer.getPets().forEach(pet -> petIds.add(pet.getId()));
        }

        customerDTO.setPetIds(petIds);

        return customerDTO;
    }

    private Customer convertDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        List<Long> petIds = customerDTO.getPetIds();

        if (petIds != null){
            List<Pet> pets = petService.list();
            customer.setPets(pets);
        }

        return customer;
    }

    private EmployeeDTO convertEmployeeToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        return employeeDTO;
    }

    private Employee convertDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        return employee;
    }
}
