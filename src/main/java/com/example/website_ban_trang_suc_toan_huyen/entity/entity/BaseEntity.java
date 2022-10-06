package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import javax.persistence.Column;
import java.time.Instant;
import java.util.Date;


public abstract class BaseEntity {
    @Column
    private Instant lastModifiedAt = Instant.now();
    @Column
    private String lastModifiedBy;
    @Column
    private String createBy;
    @Column
    private Date createAt;

}
