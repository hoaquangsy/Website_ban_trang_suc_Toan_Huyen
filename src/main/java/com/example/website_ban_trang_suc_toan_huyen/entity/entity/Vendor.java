package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
    @Id
    @Column(length = 36)
    private String vendorId;
    @Column(length = 30)
    private String name;
    @Column
    private Date createAt;
    @Column(length = 30)
    private String createBy;
    @Column
    private Date lastModifiedAt;
    @Column(length = 30)
    private String lastModifiedBy;
    @Column(length = 50)
    private String address;
    @Column(length = 50)
    private String bankName;
    @Column(length = 30)
    private String bankNumber;
    @Column(length = 30)
    private String email;

    
}
