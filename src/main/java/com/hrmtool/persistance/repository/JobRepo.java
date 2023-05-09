package com.hrmtool.persistance.repository;

import com.hrmtool.persistance.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<Job,Integer> {
}
