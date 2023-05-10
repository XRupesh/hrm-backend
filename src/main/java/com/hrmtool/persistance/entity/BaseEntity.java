package com.hrmtool.persistance.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "modify_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
        modifiedDate = new Date();
        status = true;
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = new Date();
    }

    protected void updateStatus(boolean status){
        this.status = status;
    }
}
