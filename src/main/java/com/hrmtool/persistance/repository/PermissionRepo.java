package com.hrmtool.persistance.repository;

import com.hrmtool.persistance.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepo extends JpaRepository<Permission, Integer> {
}
