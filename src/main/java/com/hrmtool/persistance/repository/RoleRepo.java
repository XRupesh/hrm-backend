package com.hrmtool.persistance.repository;

import com.hrmtool.persistance.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
