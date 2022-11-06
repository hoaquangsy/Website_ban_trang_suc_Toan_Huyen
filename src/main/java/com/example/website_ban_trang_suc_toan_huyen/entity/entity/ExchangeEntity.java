package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table (name = "exchange")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeEntity extends BaseEntity {
    @Id
    @Column
    @Type(type = "uuid-char")
    private UUID id;
    @Column
    @Type(type = "uuid-char")
    private UUID orderId;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(length = 255)
    private String reason;
    @Column(length = 255)
    private String note;

    public enum StatusEnum {
        CHO_XAC_NHAN,
        XAC_NHAN,
        THANH_CONG,
        HUY
    }
}
