package com.hrmtool.persistance.repository;

import com.hrmtool.persistance.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department,Integer> {
}
