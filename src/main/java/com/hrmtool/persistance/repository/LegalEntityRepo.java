package com.hrmtool.persistance.repository;

import com.hrmtool.persistance.entity.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalEntityRepo extends JpaRepository<LegalEntity,Integer> {
}
