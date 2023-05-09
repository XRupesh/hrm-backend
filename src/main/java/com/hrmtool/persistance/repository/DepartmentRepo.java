package com.hrmtool.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrmtool.persistance.entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer>{

}
