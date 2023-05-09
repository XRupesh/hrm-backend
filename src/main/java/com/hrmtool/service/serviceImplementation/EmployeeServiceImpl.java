package com.hrmtool.service.serviceImplementation;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hrmtool.enums.EmploymentStatus;
import com.hrmtool.persistance.dto.EmailDetails;
import com.hrmtool.persistance.dto.EmployeeDto;
import com.hrmtool.persistance.entity.Employee;
import com.hrmtool.persistance.repository.EmployeeRepo;
import com.hrmtool.service.EmployeeService;
import com.hrmtool.service.SESService;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepository;
    private final SESService sesService;

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
            EmailDetails emailDetails = EmailDetails.builder()
                    .toEmail(employee.getEmail())
                    .firstName(employee.getFirstName())
                    .body("welcome to xgileit...")
                    .build();
			 try {
	                sesService.sendEmail(emailDetails);
	            } catch (Exception e) {
	                throw new RuntimeException("Error while sending mail");
	            }
			return new EmployeeDto(employeeRepository.save(employee));
		} catch (Exception e) {
			log.error("exception occur while getting employee :{} due to :{}", employeeDto.getFirstName(),
					ExceptionUtils.getStackTrace(e));
          return null;
		}
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
     //  Employee emp = employeeRepository.findById(employeeDto.getEmployeeCode()).get();
       Employee employee = employeeDto.toEmployee();
       return new EmployeeDto(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployeeById(Integer id) {
    	Employee emp = employeeRepository.findById(id).get();
    	 emp.setEmploymentStatus(EmploymentStatus.TERMINATED);
    	 
    }
}
