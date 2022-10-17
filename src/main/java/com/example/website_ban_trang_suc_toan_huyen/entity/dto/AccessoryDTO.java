package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import com.example.website_ban_trang_suc_toan_huyen.support.enums.AccessoryStatus;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@ToString
public class AccessoryDTO {
    private UUID accessoryId;

    private String name;

    private String description;

    private String color;

    private Instant createAt;

    private String createBy;

    private Instant lastModifiedAt;

    private String lastModifiedBy;

    private AccessoryStatus status;

    private BigDecimal price;
}
