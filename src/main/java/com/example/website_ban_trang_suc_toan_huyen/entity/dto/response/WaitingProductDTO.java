package com.example.website_ban_trang_suc_toan_huyen.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaitingProductDTO {
    @Type(type = "uuid-char")
    private UUID Id;
    @Type(type = "uuid-char")
    private UUID productId;
    private String name;
    private Integer quantity;
    @Type(type = "uuid-char")
    private UUID sizeId;
    private String note;
}
