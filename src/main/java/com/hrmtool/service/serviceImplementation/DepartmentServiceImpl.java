package com.hrmtool.service.serviceImplementation;

import com.hrmtool.globalHandler.NotFoundException;
import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.DepartmentDto;
import com.hrmtool.persistance.entity.Department;
import com.hrmtool.persistance.entity.Employee;
import com.hrmtool.persistance.entity.Organization;
import com.hrmtool.persistance.repository.DepartmentRepo;
import com.hrmtool.persistance.repository.EmployeeRepo;
import com.hrmtool.persistance.repository.OrganizationRepo;
import com.hrmtool.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepo departmentRepository;
    private OrganizationRepo organizationRepository;
    private EmployeeRepo employeeRepository;

    @Override
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> getAllDepartments() {
        List<DepartmentDto> departmentList = departmentRepository.findAll().stream()
                .map(DepartmentDto::new)
                .collect(Collectors.toList());
        ApiResponse<List<DepartmentDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Department List Fetched successfully", departmentList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepartmentById(Integer departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
        ApiResponse<DepartmentDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Department Fetched successfully", new DepartmentDto(department));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(DepartmentDto departmentDto) {
        Organization organization = organizationRepository.findById(departmentDto.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found with id: " + departmentDto.getOrganizationId()));
        Department department = departmentDto.toDepartment(organization);
        Department savedDepartment = departmentRepository.save(department);
        ApiResponse<DepartmentDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Department created successfully", new DepartmentDto(savedDepartment));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<DepartmentDto>> updateDepartment(Integer departmentId, DepartmentDto departmentDto) {
        Department currentDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        currentDepartment.setName(departmentDto.getName());
        // Additional updates can be added here

        Department updatedDepartment = departmentRepository.save(currentDepartment);
        ApiResponse<DepartmentDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Department updated successfully", new DepartmentDto(updatedDepartment));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<DepartmentDto>> deleteDepartment(Integer departmentId) {
        try {
            Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new NotFoundException("Department not found with ID: " + departmentId));
            List<Employee> employees = employeeRepository.getByDepartment(department);
            if (employees.isEmpty()) {
                departmentRepository.deleteById(departmentId);
            } else {
                throw new RuntimeException("Unable to delete.There are employees in this department.");
            }
            ApiResponse<DepartmentDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Department deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (
                NotFoundException ex) {
            ApiResponse<DepartmentDto> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (
                RuntimeException ex) {
            ApiResponse<DepartmentDto> response = new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        } catch (
                Exception ex) {
            ApiResponse<DepartmentDto> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
