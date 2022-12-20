package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EventRequest {
    private String name;
    private Integer discount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
}
