package com.hrmtool.controller;

import com.hrmtool.config.Constant;
import com.hrmtool.globalHandler.response.ApiResponse;
import com.hrmtool.persistance.dto.DepartmentDto;
import com.hrmtool.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.Path.Department.DEPARTMENT_CONTROLLER,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {
    private DepartmentService departmentService;

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @PostMapping(value = Constant.Path.Department.ADD_DEPARTMENT)
    public ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(@RequestBody DepartmentDto departmentDto) {
        logger.info("Department creation request: {}", departmentDto);
        return departmentService.createDepartment(departmentDto);
    }

    @GetMapping(value = Constant.Path.Department.FETCH_BY_DEPARTMENT_ID)
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepartment(@PathVariable Integer departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    @GetMapping(value = Constant.Path.Department.FETCH_ALL_DEPARTMENT)
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PutMapping(value = Constant.Path.Department.UPDATE_DEPARTMENT)
    public ResponseEntity<ApiResponse<DepartmentDto>> updateDepartment(@PathVariable Integer departmentId, @RequestBody DepartmentDto departmentDto) {
        return departmentService.updateDepartment(departmentId,departmentDto);
    }

    @DeleteMapping(value = Constant.Path.Department.DELETE_DEPARTMENT)
    public ResponseEntity<ApiResponse<DepartmentDto>> deleteDepartment(@PathVariable Integer departmentId) {
        return departmentService.deleteDepartment(departmentId);
    }
}
