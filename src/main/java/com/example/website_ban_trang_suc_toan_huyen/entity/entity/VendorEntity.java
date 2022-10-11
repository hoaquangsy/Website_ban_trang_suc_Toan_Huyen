package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table (name = "vendor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorEntity extends BaseEntity {
    @Id
    @Column(name = "vendor_id",length = 36)
    @Type(type = "uuid-char")
    private UUID vendorId;
    @Column(length = 30)
    private String name;
    @Column(length = 50)
    private String address;
    @Column(name = "bank_name",length = 50)
    private String bankName;
    @Column(name = "bank_Number",length = 30)
    private String bankNumber;
    @Column(length = 30)
    private String email;
    @Column(length = 11)
    private String phone;
    @Column()
    private Boolean deleted;
}
