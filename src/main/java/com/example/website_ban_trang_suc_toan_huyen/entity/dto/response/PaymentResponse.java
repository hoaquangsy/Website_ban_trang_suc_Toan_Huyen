package com.example.website_ban_trang_suc_toan_huyen.entity.dto.response;

import lombok.*;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PaymentResponse {
    private String paymentUrl;
}
