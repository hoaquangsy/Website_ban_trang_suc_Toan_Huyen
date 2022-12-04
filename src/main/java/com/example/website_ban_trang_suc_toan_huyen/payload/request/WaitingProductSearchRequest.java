package com.example.website_ban_trang_suc_toan_huyen.payload.request;


import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class WaitingProductSearchRequest {
    private UUID productId;

    private UUID sizeId;

    private String startDate;

    private String endDate;

    private String keyword;

    private Integer pageSize;

    private Integer pageIndex;

    private String sortBy;
}
