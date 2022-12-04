package com.example.website_ban_trang_suc_toan_huyen.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RevenueDTO {

    private Integer month;

    private BigDecimal total;

    private Integer quantity;
}
