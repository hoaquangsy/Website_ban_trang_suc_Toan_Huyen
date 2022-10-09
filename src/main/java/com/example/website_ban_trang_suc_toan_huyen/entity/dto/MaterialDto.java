package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class MaterialDto {
    private Integer materialId;
    private String materialName;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private String color;
    private String type;
    private Integer age;
}
