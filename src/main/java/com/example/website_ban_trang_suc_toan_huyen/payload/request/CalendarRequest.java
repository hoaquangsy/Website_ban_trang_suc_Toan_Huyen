package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CalendarEntity;
import lombok.Data;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CalendarRequest {

    private LocalDateTime time;

    private CalendarEntity.Status status;

    private String userName;

    private String phoneNumber;

    private UUID productId;

    private UUID sizeId;

    private String email;

    private String note;

}
