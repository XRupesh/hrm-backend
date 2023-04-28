package com.hrmtool.persistance.repository;

import com.hrmtool.persistance.entity.RoleUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleUserMappingRepo extends JpaRepository<RoleUserMapping, Integer> {
}
