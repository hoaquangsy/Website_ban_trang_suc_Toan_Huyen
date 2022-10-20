package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String userId;
    private Date dayTrading;
    private BigDecimal customerMoney;
    private String paymentMethod;
    private Boolean purchase;
    private BigDecimal transportFee;
    private BigDecimal deposit;
    private BigDecimal total;
    private Integer status;
    private Boolean type;
    private UUID eventId;
}
