package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table (name = "vendor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer vendorId;
    @Column(length = 30)
    private String name;
    @Column(length = 50)
    private String address;
    @Column(length = 50)
    private String bankName;
    @Column(length = 30)
    private String bankNumber;
    @Column(length = 30)
    private String email;

}
