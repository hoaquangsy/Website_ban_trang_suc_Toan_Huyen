package com.example.website_ban_trang_suc_toan_huyen.payload.request;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CalendarEntity;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CalendarSearchRequest {

    private String keyword;

    private UUID productId;

    private String startDate;

    private CalendarEntity.Status status;

    private String endDate;

    private Integer pageIndex = 1;

    private Integer pageSize = 10;

    private String sortBy;
}
