package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
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

    private Instant createAt;

    private String createBy;

    private Instant lastModifiedAt;

    private String lastModifiedBy;

}
