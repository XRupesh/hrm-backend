package com.hrmtool.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "Job")
public class Job extends BaseEntity  {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Integer jobId;

    @Column(name = "name")
    private String name;
}
