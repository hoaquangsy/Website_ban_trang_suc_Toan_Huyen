package com.example.website_ban_trang_suc_toan_huyen.entity.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductOrderDto {
    private UUID id;

    private UUID productId;

    private String nameProduct;

    private List<String> imageUrl;

    private UUID sizeId;

    private String size;

    private BigDecimal price;

    private Integer quantity;


}
