package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventSearchRequest {
    private String keyword = "";

    private String startDate;

    private String endDate;

    private Integer pageIndex = 1;

    private Integer pageSize = 10;

    private String sortBy;
}
