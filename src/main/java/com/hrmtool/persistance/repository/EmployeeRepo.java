package com.hrmtool.persistance.repository;

import com.hrmtool.persistance.entity.Department;
import com.hrmtool.persistance.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    List<Employee> getByDepartment(Department department);
}
