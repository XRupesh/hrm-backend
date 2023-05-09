package com.hrmtool.persistance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hrmtool.persistance.entity.Department;
import com.hrmtool.persistance.entity.Organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    @JsonProperty("department_id")
    private Integer departmentId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("organization_id")
    private Integer organizationId;

    public DepartmentDto(Department department) {
        this.departmentId = department.getDepartmentId();
        this.name = department.getName();
        if (department.getOrganization() != null) {
            this.organizationId = department.getOrganization().getOrganizationCode();
        }
    }

    public Department toDepartment(Organization organization) {
        return Department.builder()
                .departmentId(this.departmentId)
                .name(this.name)
                .organization(organization)
                .build();
    }
}
