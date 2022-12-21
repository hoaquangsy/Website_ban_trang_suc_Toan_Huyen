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
    @NotNull(message = "Người dùng không để trống")
    private UUID userId;
    @NotNull
    private BigDecimal customerMoney;
    @NotNull(message = "Phương thức thanh toán không để trống")
    private OrderEntity.PaymentMethod paymentMethod;
    @NotNull(message = "Phí vận chuyển không để trống")
    private BigDecimal transportFee;
    @NotNull(message = "Tổng tiền không để trống")
    private BigDecimal total;
    @NotNull(message = "Loại mua hàng không để trống")
    private OrderEntity.OrderType purchaseType;

    @NotNull(message = "Trạng thái không để trống")
    private OrderEntity.StatusEnum status;

    private UUID eventId;

    private String createBy;
    @NotNull(message = "Địa chỉ không để trống")
    private String address;

    private BigDecimal cost;

    @NotEmpty(message = "Hiện không có sản phẩm trong hóa đơn này")
    private List<OrderDetailRq> orderDetailList;

    @Data
    @Valid
    public static class OrderDetailRq{
        @NotNull
        private UUID productId;
        @NotNull
        private Integer quantity;
        @NotNull
        private BigDecimal price;

        private BigDecimal discount;
        @NotNull
        private BigDecimal total;
        @NotNull
        private UUID sizeId;
    }




}
