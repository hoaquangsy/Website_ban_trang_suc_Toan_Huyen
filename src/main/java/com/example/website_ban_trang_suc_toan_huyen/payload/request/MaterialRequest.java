package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
    @NotBlank(message = "color is required")
    private String type;
    @NotNull(message = "age is required")
    private Integer age;
}