package com.hrmtool.service;

import org.springframework.http.ResponseEntity;

import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.EmployeeDto;

public interface EmployeeService {
    ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeById(Integer id);

    ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(EmployeeDto employeeDto);

    ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(EmployeeDto employeeDto);

    void deleteEmployeeById(Integer id);

}
