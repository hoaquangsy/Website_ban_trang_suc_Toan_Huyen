package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Date;

@Data
public class ProductDto {
    private Integer productId;
    private String categoryId;
    private String vendorId;
    private String code;
    private String nameProduct;
    private float weight;
    private BigDecimal purchasePrice;
    private BigDecimal saleprice;
    private String status;
    private String note;
    private BigDecimal salary;
    private String accessoryId;
    private String gender;
    private Date createAt;
    private Date lastModifiedAt;
    private String lastModifiedBy;

}
