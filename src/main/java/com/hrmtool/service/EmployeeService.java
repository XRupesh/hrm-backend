package com.hrmtool.service;

import com.hrmtool.persistance.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto getEmployeeById(Integer id);

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto updateEmployee(EmployeeDto employeeDto);

    void deleteEmployeeById(Integer id);

}
