package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.UUID;

@Data
public class RefundDto {
    private String refundId;
    private UUID orderId;
    private UUID productId;
    private Boolean status;
    private String reason;
    private String note;
}
