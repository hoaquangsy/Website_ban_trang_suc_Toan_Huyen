package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Data
public class ProductSizeDto {
    private Integer idProductSize;
    private Integer sizId;
    private Integer productId;
    private Integer quantity;

}
