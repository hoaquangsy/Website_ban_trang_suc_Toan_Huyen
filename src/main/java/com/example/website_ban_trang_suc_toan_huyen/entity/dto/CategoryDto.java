package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Data
@ToString
public class CategoryDto {

    private UUID categoryId;

    private String name;

    private String description;

    private List<CategoryPropertyDTO> properties;

    private Boolean deleted;

    private Instant createAt;

    private String createBy;

    private Instant lastModifiedAt;

    private String lastModifiedBy;

}
