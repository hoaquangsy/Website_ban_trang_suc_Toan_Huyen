package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Date;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseEntity {

    @Column
    protected Date lastModifiedAt ;
    @Column
    protected String lastModifiedBy;
    @Column(updatable = false)
    protected String createBy;
    @Column(updatable = false)
    protected Date createAt;
}
