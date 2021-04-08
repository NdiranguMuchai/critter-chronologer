package com.ndirangu.critterchronologer.service;

import com.ndirangu.critterchronologer.model.Employee;
import com.ndirangu.critterchronologer.model.EmployeeSkill;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    Employee save(Employee employee);
    Employee findById(Long employeeId)throws Exception;
    void setAvailability(Set<DayOfWeek> availability, Long employeeId) throws Exception;
    Set<Employee> findEmployeesForService(LocalDate localDate, HashSet<EmployeeSkill> skills);
    List<Employee> list();
}
