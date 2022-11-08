package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import lombok.Data;

import java.util.UUID;

@Data
public class ExchangeSearchRequest {
    private UUID orderId;

    private ExchangeEntity.StatusEnum status;

    private String keyword  = "";

    private String startDate;

    private  String endDate;

    private Integer pageIndex = 1;

    private Integer pageSize = 10;

    private String sortBy;
}
