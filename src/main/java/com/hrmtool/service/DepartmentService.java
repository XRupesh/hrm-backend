package com.hrmtool.service;

import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.DepartmentDto;
import com.hrmtool.persistance.dto.OrganizationDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {
    ResponseEntity<ApiResponse<List<DepartmentDto>>> getAllDepartments();
    ResponseEntity<ApiResponse<DepartmentDto>> getDepartmentById(Integer departmentId);
    ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(DepartmentDto departmentDto);
    ResponseEntity<ApiResponse<DepartmentDto>> updateDepartment(Integer departmentId, DepartmentDto departmentDto);
    ResponseEntity<ApiResponse<DepartmentDto>> deleteDepartment(Integer departmentId);
}
