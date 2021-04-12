package com.ndirangu.critterchronologer.converter;

import com.ndirangu.critterchronologer.dto.EmployeeDTO;
import com.ndirangu.critterchronologer.model.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Refactored convert methods here to make controller class cleaner.
 */

@Service
public class EmployeeDTOConverter {
    public EmployeeDTO convertEmployeeToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        return employeeDTO;
    }

    public Employee convertDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        return employee;
    }
}
