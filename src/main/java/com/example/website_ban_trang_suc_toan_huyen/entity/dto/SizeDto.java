package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SizeDto {
    private UUID sizeId;

    private String size;

    private String description;

    private Boolean deleted;

    private Instant createAt;

    private String createBy;

    private Instant lastModifiedAt;

    private String lastModifiedBy;
}
