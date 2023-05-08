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
@Table(name = "Department")
public class Department extends BaseEntity 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "name")
    private String name;

}
