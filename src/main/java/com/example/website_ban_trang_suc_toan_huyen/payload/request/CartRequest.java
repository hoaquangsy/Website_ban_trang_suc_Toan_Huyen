package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class CartRequest {

    @NotNull(message = "amount is required")
    @Min(value = 1)
    private Integer amount;

    @NotNull(message = "productId is required")
    private UUID productId;

    @NotNull(message = "userId is required")
    private UUID userId;

    @NotNull(message = "sizeId is required")
    private UUID sizeId;
}