package com.hrmtool.persistance.repository;

import com.hrmtool.persistance.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepo extends JpaRepository<Organization, Integer> {
    Optional<Organization> findByDomain(String domain);
}
