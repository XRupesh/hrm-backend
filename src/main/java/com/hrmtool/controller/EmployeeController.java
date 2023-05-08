package com.hrmtool.controller;

import com.hrmtool.config.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.Path.Employee.EMPLOYEE_CONTROLLER,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {
}
