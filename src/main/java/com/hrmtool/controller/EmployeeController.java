package com.hrmtool.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrmtool.config.Constant;
import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.EmployeeDto;
import com.hrmtool.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.Path.Employee.EMPLOYEE_CONTROLLER,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeById(@PathVariable Integer id) {
        return  employeeService.getEmployeeById(id);
    }

    /**
     * @param employeeDto
     * @return
     */
    @PostMapping("create")
    public ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.createEmployee(employeeDto);
    }

    /**
     * @param id
     * @param employeeDto
     * @return
     */
    @PutMapping("update")
    public ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDto employeeDto) {
        employeeDto.setEmployeeCode(id);
       return employeeService.updateEmployee(employeeDto);
     
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }
}
