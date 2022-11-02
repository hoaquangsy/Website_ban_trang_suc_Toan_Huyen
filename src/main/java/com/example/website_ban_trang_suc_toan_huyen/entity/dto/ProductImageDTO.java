package com.example.website_ban_trang_suc_toan_huyen.entity.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class ProductImageDTO {
    private UUID productImageId;

    private String imageUrl;

    private UUID productId;

    private Boolean deleted;
}
