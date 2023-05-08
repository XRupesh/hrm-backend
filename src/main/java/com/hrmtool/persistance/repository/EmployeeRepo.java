package com.hrmtool.persistance.repository;

import com.hrmtool.persistance.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
}
