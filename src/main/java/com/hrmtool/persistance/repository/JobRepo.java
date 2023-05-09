package com.hrmtool.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrmtool.persistance.entity.Job;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepo extends JpaRepository<Job, Integer>{

}
