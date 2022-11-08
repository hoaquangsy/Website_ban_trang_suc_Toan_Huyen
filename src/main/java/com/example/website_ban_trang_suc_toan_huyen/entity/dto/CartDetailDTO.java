package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDTO {
    private UUID id;
    private Integer amount;
    private UUID productId;
    private UUID cartId;
    private Size size;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Size{
        private UUID id;
        private String name;
    }
}
