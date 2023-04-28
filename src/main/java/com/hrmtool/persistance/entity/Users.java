package com.hrmtool.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    private String address;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "is_guest_user")
    private Boolean isGuestUser;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "linked_in")
    private String linkedIn;

    @Column(name = "modify_by")
    private String modifyBy;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "primary_email")
    private String primaryEmail;

    @Column(name = "user_type")
    private String userType;

    private String username;

    @Column(name = "logged_in_time")
    private Date loggedInTime;

    private String password;

    @Column(name = "is_password_reset_required")
    private Boolean isPasswordResetRequired;

    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
