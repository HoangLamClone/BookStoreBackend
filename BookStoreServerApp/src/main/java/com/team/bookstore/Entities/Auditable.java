package com.team.bookstore.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable{
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "create_at",nullable = false,updatable = false)
    Date createAt;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "update_at")
    Date updateAt;
    @CreatedBy
    @Column(name = "create_by" , nullable = false,updatable = false)
    String createBy;
    @LastModifiedBy
    @Column(name = "updateBy")
    String updateBy;
}
