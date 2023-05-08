package com.hrmtool.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization")
public class Organization extends BaseEntity {

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

    public void updateStatus(boolean status) {
        super.updateStatus(status);
    }
}
