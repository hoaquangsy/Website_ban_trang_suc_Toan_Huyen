package com.example.website_ban_trang_suc_toan_huyen.payload.request;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class SizeProduct {

    @NotNull(message = "SizeId not found")
    private UUID sizeId;

    @NotNull(message = "weight not found")
    private Float weight;

    @NotNull(message = "purchasePrice not found")
    private BigDecimal purchasePrice;

    @NotNull(message = "salePrice not found")
    private BigDecimal  salePrice;

    @NotNull(message = "quantity not found")
    private Integer quantity;
}
