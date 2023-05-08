package com.hrmtool.persistance.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_code")
    private Integer employeeCode;

    //Employee Personal Details

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "address")
    private String street;

    //Employee-Organization details

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "designation")
    private String designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reports_to_id")
    private Employee reportsTo;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "salary_type")
    private String salaryType;

    @Column(name = "employment_status")
    private String employmentStatus;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_legal_entity",
            joinColumns = @JoinColumn(name = "employee_code"),
            inverseJoinColumns = @JoinColumn(name = "legal_entity_code")
    )
    private Set<LegalEntity> legalEntities = new HashSet<>();

}
