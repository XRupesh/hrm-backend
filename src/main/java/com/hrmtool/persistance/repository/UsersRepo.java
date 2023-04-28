package com.hrmtool.persistance.repository;

import com.hrmtool.persistance.entity.Organization;
import com.hrmtool.persistance.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepo extends JpaRepository<Users,Integer> {
    List<Users> getByOrganization(Organization organization);
}
