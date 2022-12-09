package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CalendarEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
public class CalendarDTO {

    private UUID id;

    private LocalDateTime time;

    private CalendarEntity.Status status;

    private String userName;

    private String phoneNumber;

    private UUID productId;

    private ProductDto product;

    private SizeDto size;

    private UUID sizeId;

    private String email;

    private String note;
}