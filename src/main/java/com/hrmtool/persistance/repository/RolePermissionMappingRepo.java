package com.hrmtool.persistance.repository;

import com.hrmtool.persistance.entity.RolePermissionMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionMappingRepo extends JpaRepository<RolePermissionMapping, Integer> {
}
