package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class MaterialRequest {

    @NotBlank(message = "Material is required")
    private String materialName;

    @NotNull(message = "purchasePrice is required")
    @DecimalMin(value = "0.0")
    private BigDecimal purchasePrice;

    @NotNull(message = "salePrice is required")
    @DecimalMin(value = "0.0")
    private BigDecimal salePrice;

    @NotBlank(message = "color is required")
    private String color;

    @NotNull(message = "MaterialType is required")
    private MaterialEntity.MaterialType type;

    @NotNull(message = "age is required")
    private Integer age;
}
