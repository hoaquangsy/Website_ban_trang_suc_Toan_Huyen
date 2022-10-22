package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import com.example.website_ban_trang_suc_toan_huyen.util.EnumValidator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderRequest {
    @NotNull
    private UUID userId;
    @NotNull
    private BigDecimal customerMoney;
    @NotNull
    private OrderEntity.PaymentMethod paymentMethod;
    @NotNull
    private BigDecimal transportFee;
    @NotNull
    private BigDecimal total;
    @NotNull
    private OrderEntity.OrderType purchaseType;

    @NotNull
    private OrderEntity.StatusEnum status;

    private UUID eventId;

    @NotNull
    private String address;
    @NotEmpty
    private List<@Valid OrderDetailRq> orderDetailList;

    @Data
    @Valid
    public static class OrderDetailRq{
        @NotNull
        private UUID productId;
        @NotNull
        private Integer amount;
        @NotNull
        private BigDecimal price;
        @NotNull
        private BigDecimal discount;
        @NotNull
        private BigDecimal total;
        @NotNull
        private UUID sizeId;
    }




}
