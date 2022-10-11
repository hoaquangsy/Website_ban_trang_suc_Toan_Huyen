package com.example.website_ban_trang_suc_toan_huyen.entity.dto;


import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import java.util.UUID;

@Data
public class CategoryPropertyDTO {
    private UUID categoryPropertyId;
    @Column(length = 30)
    private String name;
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID categoryId;
}
