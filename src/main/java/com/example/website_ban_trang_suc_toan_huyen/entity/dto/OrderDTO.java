package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UUID id;

    private UUID userId;

    private UserDTO user;

    private Long orderCode;

    private BigDecimal customerMoney;

    private OrderEntity.PaymentMethod paymentMethod;

    private BigDecimal transportFee;

    private BigDecimal total;

    private OrderEntity.StatusEnum status;

    private OrderEntity.OrderType purchaseType;

    private UUID eventId;

    private EventDto event;

    private String address;

    private Instant createAt;

    private String createBy;

    private Instant lastModifiedAt;

    private String lastModifiedBy;
}
