package com.hrmtool.persistance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hrmtool.persistance.entity.Organization;
import com.hrmtool.persistance.entity.Users;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDto {

    //Organization Details
    @JsonProperty("organization_code")
    private Integer organizationCode;
    private String name;
    private String domain;
    private Integer size;
    private String country;
    @JsonProperty("contact_num")
    private Integer contactNum;


    //User Details
    private Integer userId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;

    private String password;
    @JsonProperty("primary_email")
    private String primaryEmail;



    public OrganizationDto(Organization organization, Users users) {
        this.organizationCode = organization.getOrganizationCode();
        this.name = organization.getName();
        this.domain = organization.getDomain();
        this.size = organization.getSize();
        this.country = organization.getCountry();
        this.contactNum = organization.getContactNum();
        this.userId = users.getUserId();
        this.firstName = users.getFirstName();
        this.lastName = users.getLastName();
        this.primaryEmail = users.getPrimaryEmail();
        this.password = "*********";
    }

    public OrganizationDto(Organization organization) {
        this.organizationCode = organization.getOrganizationCode();
        this.name = organization.getName();
        this.domain = organization.getDomain();
        this.size = organization.getSize();
        this.country = organization.getCountry();
        this.contactNum = organization.getContactNum();
    }
}
