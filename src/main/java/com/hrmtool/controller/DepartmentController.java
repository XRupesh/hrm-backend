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

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(@RequestBody DepartmentDto departmentDto) {
        logger.info("Department creation request: {}", departmentDto);
        return departmentService.createDepartment(departmentDto);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepartment(@PathVariable Integer departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<DepartmentDto>> updateDepartment(@PathVariable Integer departmentId, @RequestBody DepartmentDto departmentDto) {
        return departmentService.updateDepartment(departmentId,departmentDto);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<DepartmentDto>> deleteDepartment(@PathVariable Integer departmentId) {
        return departmentService.deleteDepartment(departmentId);
    }
}
