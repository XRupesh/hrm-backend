package com.hrmtool.controller;

import com.hrmtool.config.Constant;
import com.hrmtool.persistance.dto.EmployeeDto;
import com.hrmtool.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.Path.Employee.EMPLOYEE_CONTROLLER,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Integer id) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeDto);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto createdEmployeeDto = employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployeeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDto employeeDto) {
        employeeDto.setEmployeeCode(id);
        EmployeeDto updatedEmployeeDto = employeeService.updateEmployee(employeeDto);
        return ResponseEntity.ok(updatedEmployeeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }
}
