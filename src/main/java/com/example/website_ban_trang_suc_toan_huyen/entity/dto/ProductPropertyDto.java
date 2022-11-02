package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductPropertyDto {
    private UUID productPropertyId;

    private String name;

    private String value;

    private UUID productId;

    private Boolean deleted;
}
