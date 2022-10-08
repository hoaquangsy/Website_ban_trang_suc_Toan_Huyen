package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccessoryDto {
    private Integer accessoryId;
    private String name;
    private BigDecimal price;
    private String description;
}
