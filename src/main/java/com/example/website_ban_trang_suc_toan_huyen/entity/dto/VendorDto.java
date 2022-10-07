package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Data
public class VendorDto {
    private UUID vendorId;
    private String name;
    private String address;
    private String bankName;
    private String bankNumber;
    private String email;
    private String phone;
    private Date createAt;
    private String createBy;
    private Date lastModifiedAt;
    private String lastModifiedBy;

}
