package com.example.website_ban_trang_suc_toan_huyen.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductStatistical {

    private String name;

    private Long quantity;

    private Double persent;

    public ProductStatistical(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
