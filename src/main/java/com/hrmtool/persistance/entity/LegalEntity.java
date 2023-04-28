package com.hrmtool.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "legal_entity")
public class LegalEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "manager")
    private String manager;

    @Column(name = "registration_num")
    private Integer registrationNum;

    @Column(name = "tax_num")
    private Integer taxNum;

    @Column(name = "gst_number")
    private Integer gstNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "concact_num")
    private Integer contactNum;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
