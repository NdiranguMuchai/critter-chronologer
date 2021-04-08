package com.ndirangu.critterchronologer.service;

import com.ndirangu.critterchronologer.model.Customer;
import com.ndirangu.critterchronologer.model.Employee;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.model.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule create(Schedule schedule);
    List<Schedule> list();
    List<Schedule> findScheduleByPet(Pet pet);
    List<Schedule> findScheduleByEmployee(Employee employee);
    List<Schedule> findScheduleByCustomer(Customer customer);
}
