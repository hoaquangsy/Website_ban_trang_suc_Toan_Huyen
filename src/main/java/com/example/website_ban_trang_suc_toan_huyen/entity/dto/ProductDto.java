package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Date;


@Data
public class ProductDto {
    @Column(length = 36)
    private String productId;
    @Column(length = 36)
    private String categoryId;
    @Column(length = 36)
    private String vendorId;
    @Column(length = 36)
    private String code;
    @Column(length = 50)
    private String nameProduct;
    @Column
    private float weight;
    @Column
    private BigDecimal purchasePrice;
    @Column
    private BigDecimal saleprice;
    @Column(length = 50)
    private String status;
    @Column
    private Integer amount;
    @Column(length = 50)
    private String note;
    @Column
    private BigDecimal salary;
    @Column(length = 36)
    private String accessoryId;
    @Column(length = 30)
    private String gender;
    private Date createAt;
    private Date lastModifiedAt;
    private String lastModifiedBy;

    
}
