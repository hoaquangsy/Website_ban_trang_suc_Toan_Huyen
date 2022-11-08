package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CartDetailDTO {
    private UUID id;
    private Integer amount;
    private UUID productId;
    private UUID cartId;
}