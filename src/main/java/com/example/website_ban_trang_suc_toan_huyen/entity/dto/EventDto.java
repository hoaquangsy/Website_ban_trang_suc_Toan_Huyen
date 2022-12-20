package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventDto {

    private UUID eventId;
    private String code;

    private String name;

    private Integer discount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String description;
    
    private Instant createAt;

    private String createBy;

    private Instant lastModifiedAt;

    private String lastModifiedBy;

    private Boolean deleted;
}
