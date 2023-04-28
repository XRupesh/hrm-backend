package com.hrmtool.persistance.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization")
public class Organization extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_code")
    private Integer organizationCode;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_num")
    private Integer contactNum;

    @Column(name = "size")
    private Integer size;

    @Column(name = "country")
    private String country;

    @Column(name = "domain")
    private String domain;
}
