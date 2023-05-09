package com.hrmtool.persistance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hrmtool.persistance.entity.Job;
import com.hrmtool.persistance.entity.Organization;
import com.hrmtool.persistance.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {

    @JsonProperty("job_id")
    private Integer jobId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("role_id")
    private Integer roleId;

    @JsonProperty("organization_id")
    private Integer organizationId;

    public JobDto(Job job) {
        this.jobId = job.getJobId();
        this.name = job.getName();
        if (job.getRole() != null) {
            this.roleId = job.getRole().getRoleId();
        }
        if (job.getOrganization() != null) {
            this.organizationId = job.getOrganization().getOrganizationCode();
        }
    }

    public Job toJob(Role role, Organization organization) {
        return Job.builder()
                .jobId(this.jobId)
                .name(this.name)
                .role(role)
                .organization(organization)
                .build();
    }
}
