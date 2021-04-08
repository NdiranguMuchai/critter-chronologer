package com.ndirangu.critterchronologer.repository;

import com.ndirangu.critterchronologer.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Set<Employee> findEmployeeByDaysAvailable(DayOfWeek dayOfWeek);
}
