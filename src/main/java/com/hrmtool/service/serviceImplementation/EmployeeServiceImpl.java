package com.hrmtool.service.serviceImplementation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hrmtool.enums.EmploymentStatus;
import com.hrmtool.persistance.dto.EmployeeDto;
import com.hrmtool.persistance.entity.Employee;
import com.hrmtool.persistance.repository.EmployeeRepo;
import com.hrmtool.service.EmployeeService;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepository;


    @Override
	public EmployeeDto getEmployeeById(Integer id) {
      log.info("going to get employee from id :{} ",id);
		try {
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("employee not found with ID: " + id));
			return new EmployeeDto(employee);

		} catch (Exception e) {
			log.error("exception occur while getting employee from id {} due to :{}", id,
					ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
			return null;
		}

	}

    @Override
    public  EmployeeDto createEmployee(EmployeeDto employeeDto) {
    	try {
			Employee employee = employeeDto.toEmployee();
			employeeRepository.save(employee);
			return new EmployeeDto(employeeRepository.save(employee));
		} catch (Exception e) {
			log.error("exception occur while getting employee :{} due to :{}", employeeDto.getFirstName(),
					ExceptionUtils.getStackTrace(e));
          return null;
		}
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public void deleteEmployeeById(Integer id) {
    	Employee emp = employeeRepository.findById(id).get();
    	 emp.setEmploymentStatus(EmploymentStatus.TERMINATED);
    	 
    }
}
