package com.hrmtool.persistance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hrmtool.persistance.entity.Employee;
import com.hrmtool.persistance.entity.LegalEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    @JsonProperty("employee_code")
    private Integer employeeCode;

    //Employee Personal Details

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("birth_date")
    private LocalDate birthDate;

    private String gender;

    @JsonProperty("marital_status")
    private String maritalStatus;

    private String address;

    //Employee-Organization details

    @JsonProperty("job_title")
    private String jobTitle;

    private String designation;

    @JsonProperty("reports_to_id")
    private Integer reportsToId;

    private Double salary;

    @JsonProperty("salary_type")
    private String salaryType;

    @JsonProperty("employment_status")
    private String employmentStatus;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("legal_entities")
    private Set<Integer> legalEntityIds = new HashSet<>();

    public EmployeeDto(Employee employee) {
        this.employeeCode = employee.getEmployeeCode();
        this.firstName = employee.getFirstName();
        this.middleName = employee.getMiddleName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.phoneNumber = employee.getPhoneNumber();
        this.birthDate = employee.getBirthDate();
        this.gender = employee.getGender();
        this.maritalStatus = employee.getMaritalStatus();
        this.address = employee.getStreet();
        this.jobTitle = employee.getJobTitle();
        this.designation = employee.getDesignation();
        if (employee.getReportsTo() != null) {
            this.reportsToId = employee.getReportsTo().getEmployeeCode();
        }
        this.salary = employee.getSalary();
        this.salaryType = employee.getSalaryType();
        this.employmentStatus = employee.getEmploymentStatus();
        this.startDate = employee.getStartDate();
        this.endDate = employee.getEndDate();
        if (employee.getLegalEntities() != null && !employee.getLegalEntities().isEmpty()) {
            this.legalEntityIds = employee.getLegalEntities().stream()
                    .map(LegalEntity::getId)
                    .collect(Collectors.toSet());
        }
    }

    public Employee toEmployee() {
        return Employee.builder()
                .employeeCode(this.employeeCode)
                .firstName(this.firstName)
                .middleName(this.middleName)
                .lastName(this.lastName)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .birthDate(this.birthDate)
                .gender(this.gender)
                .maritalStatus(this.maritalStatus)
                .street(this.address)
                .jobTitle(this.jobTitle)
                .designation(this.designation)
                .salary(this.salary)
                .salaryType(this.salaryType)
                .employmentStatus(this.employmentStatus)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }

}
