package com.ndirangu.critterchronologer.repository;

import com.ndirangu.critterchronologer.model.Employee;
import com.ndirangu.critterchronologer.model.Pet;
import com.ndirangu.critterchronologer.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findScheduleByPets(Pet pet);
    List<Schedule> findScheduleByEmployees(Employee employee);
}
