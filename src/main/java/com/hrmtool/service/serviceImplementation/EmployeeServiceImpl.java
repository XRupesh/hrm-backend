package com.hrmtool.service.serviceImplementation;
import java.util.Optional;

import com.hrmtool.globalHandler.NotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hrmtool.enums.EmploymentStatus;
import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.EmailDetails;
import com.hrmtool.persistance.dto.EmployeeDto;
import com.hrmtool.persistance.entity.Department;
import com.hrmtool.persistance.entity.Employee;
import com.hrmtool.persistance.entity.Job;
import com.hrmtool.persistance.repository.DepartmentRepo;
import com.hrmtool.persistance.repository.EmployeeRepo;
import com.hrmtool.persistance.repository.JobRepo;
import com.hrmtool.service.EmployeeService;
import com.hrmtool.service.SESService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepository;
    private final SESService sesService;
    private final JobRepo jobRepo;
    private final DepartmentRepo departmentRepo;

    @Override
	public  ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeById(Integer id) {
      log.info("going to get employee from id :{} ",id);
		try {
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("employee not found with ID: " + id));
			ApiResponse<EmployeeDto> response = new ApiResponse<>(HttpStatus.OK.value(), "employee retrive successfully", new EmployeeDto(employee));
            return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			log.error("exception occur while getting employee from id {} due to :{}", id,
					ExceptionUtils.getStackTrace(e));
			 ApiResponse<EmployeeDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
	            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

	}

    @Override
    public   ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(EmployeeDto employeeDto) {
    	try {
			Employee employee = toEmployee(employeeDto);
			Employee updateEmployee = employeeRepository.save(employee);
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
			 ApiResponse<EmployeeDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), "employee created successfully", new EmployeeDto(updateEmployee));
	            return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("exception occur while creating employee :{} due to :{}", employeeDto.getFirstName(),
					ExceptionUtils.getStackTrace(e));
			 ApiResponse<EmployeeDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
	            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @Override
    public  ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(EmployeeDto employeeDto) {
      try {
      Employee employee = toEmployee(employeeDto);
        employee.setStatus(employeeDto.getStatus());
        
        ApiResponse<EmployeeDto> response = new ApiResponse<>(HttpStatus.OK.value(), "employee update successfully",  new EmployeeDto(employeeRepository.save(employee)));
        return new ResponseEntity<>(response, HttpStatus.OK);
        }
      catch (Exception e) {
    	  log.error("cann't update due to :{}",ExceptionUtils.getStackTrace(e));
    	  ApiResponse<EmployeeDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
          return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    	
    }

    @Override
    public void deleteEmployeeById(Integer id) {
    	Employee emp = employeeRepository.findById(id).get();
    	 emp.setEmploymentStatus(EmploymentStatus.TERMINATED);
    	 
    }
    
      private Employee toEmployee(EmployeeDto empDto) {
		
		Optional<Job>  empJob= jobRepo.findById(empDto.getJob());
		Optional<Department>  empDepartment= departmentRepo.findById(empDto.getDepartment());
		Job job=null;
		Department department=null;
		if(empJob.isPresent())
		{
			job=empJob.get();
		}
		if(empDepartment.isPresent())
		{
		 department =empDepartment.get();
		}
		
		return  Employee.builder().employeeCode(empDto.getEmployeeCode()).firstName(empDto.getFirstName()).middleName(empDto.getMiddleName())
				.lastName(empDto.getLastName()).email(empDto.getEmail()).phoneNumber(empDto.getPhoneNumber()).birthDate(empDto.getBirthDate())
				.gender(empDto.getGender()).maritalStatus(empDto.getMaritalStatus()).street(empDto.getAddress())
				.job(job).department(department).salary(empDto.getSalary()).salaryType(empDto.getSalaryType())
				.EmploymentStatus(EmploymentStatus.valueOf(empDto.getEmploymentStatus())).startDate(empDto.getStartDate()).endDate(empDto.getEndDate()).build();

	}
  
}
