package com.hrmtool.service.serviceImplementation;

import com.hrmtool.persistance.dto.EmployeeDto;
import com.hrmtool.persistance.entity.Employee;
import com.hrmtool.persistance.repository.EmployeeRepo;
import com.hrmtool.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepository;


    @Override
    public EmployeeDto getEmployeeById(Integer id) {
       return null;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public void deleteEmployeeById(Integer id) {
    }
}
