package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportPdfDTO {
    private String name;
    private Integer quantity;
    private Integer Age;
    private Float wight;
    private BigDecimal price;
    private BigDecimal wage;
    private BigDecimal total;
}
