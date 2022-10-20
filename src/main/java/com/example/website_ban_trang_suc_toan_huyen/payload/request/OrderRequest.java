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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderRequest {
    @NotNull
    private UUID userId;
    @NotNull
    private Date dayTrading;
    @NotNull
    private BigDecimal customerMoney;
    @NotNull
    @EnumValidator(enumClass = OrderEntity.PaymentMethod.class,message = "Sai định dạng payment method")
    private String paymentMethod;
    @NotNull
    private Boolean purchase;
    @NotNull
    private BigDecimal transportFee;
    @NotNull
    private BigDecimal deposit;
    @NotNull
    private BigDecimal total;
    @NotNull
    private Boolean type;
    private UUID eventId;
    @NotEmpty
    private List<@Valid OrderDetailRq> orderDetailList;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
    }




}
