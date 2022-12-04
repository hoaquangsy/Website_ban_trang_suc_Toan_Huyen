package com.example.website_ban_trang_suc_toan_huyen.entity.dto.response;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.SizeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaitingProductDTO {

    @Type(type = "uuid-char")
    private UUID Id;

    @Type(type = "uuid-char")
    private UUID productId;

    private SizeDto size;

    private ProductDto product;

    private Integer quantity;

    @Type(type = "uuid-char")
    private UUID sizeId;

    private String note;

    private Instant createAt;

    private String createBy;

    private Instant lastModifiedAt;

    private String lastModifiedBy;
}
