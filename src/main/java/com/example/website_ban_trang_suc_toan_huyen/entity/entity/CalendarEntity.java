package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "calendar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEntity extends BaseEntity{
    @Id
    @Type(type = "uuid-char")
    @Column(length = 36)
    private UUID id;
    @Column
    private LocalDateTime time;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column
    private String userName;
    @Column(length = 12)
    private String phoneNumber;
    @Column
    private String email;
    @Column
    @Type(type = "uuid-char")
    private UUID productId;
    @Column
    @Type(type = "uuid-char")
    private UUID sizeId;

    @Column
    private String note;
    @Getter
    public enum Status{
        WAIT_CONFIRM,
        ACTIVE,
        DONE,
        CLOSE;
    }
}