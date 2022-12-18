package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.example.website_ban_trang_suc_toan_huyen.support.enums.AccessoryStatus;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AccessoryRequest extends BaseRequest {

    @NotBlank(message = "Accessory name is required")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @NotBlank(message = "Color is required")
    private String color;

    private String description;

    private AccessoryStatus status;

}
