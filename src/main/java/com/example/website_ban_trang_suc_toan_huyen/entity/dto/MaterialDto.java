package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class MaterialDto {

    private UUID materialId;

    private String materialName;

    private BigDecimal purchasePrice;

    private BigDecimal salePrice;

    private String color;

    private MaterialEntity.MaterialType type;

    private Integer age;

    private MaterialEntity.StatusEnum status;

    private Instant createAt;

    private Instant lastmodifiedAt;

    private String createBy;

    private String lastmodifiedBy;
}
