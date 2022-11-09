package com.example.website_ban_trang_suc_toan_huyen.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCartResponse {
    private UUID cartId;
    private UUID userId;
    private List<CartDetailResponse> cartDetailResponseList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CartDetailResponse{
        private UUID cartDetailId;
        private UUID productId;
        private String productName;
        private String image;
        private UUID sizeId;
        private String sizeName;
        private Integer amount;
        private String code;
        private BigDecimal price;
    }
}
