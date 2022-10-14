package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class EventRequest {
    private String name;
    private Integer discount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
