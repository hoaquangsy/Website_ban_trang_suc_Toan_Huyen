package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Date;
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
@Data
public  class BaseEntity {
    private Date lastModifiedAt;
    private String lastModifiedBy;
    private String createBy;
    private Date createAt;
}
