package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Data
public class ProductSizeDto {

    private UUID idProductSize;

    private UUID sizeId;

    private String size;

    private Float weight;

    private BigDecimal purchasePrice;

    private BigDecimal salePrice;

    private UUID productId;

    private Integer quantity;

}
