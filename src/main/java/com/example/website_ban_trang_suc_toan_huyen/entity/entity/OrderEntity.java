package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table (name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends BaseEntity{
    @Id
    @Type(type = "uuid-char")
    @Column(length = 36)
    private UUID id;
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID userId;
    @Column
    private Long orderCode;
    @Column
    private BigDecimal customerMoney;
    @Column
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column
    private BigDecimal transportFee;
    @Column
    private BigDecimal total;
    @Column
    private BigDecimal cost;
    @Column
    private String phoneNumber;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column
    private OrderType purchaseType;
    @Column
    private String address;
    @Column
    private BigDecimal discount;
    @Column
    private Boolean isRepurchase;
    @Column
    @Type(type ="uuid-char")
    private UUID eventId;
    @Column
    private String note;

    @AllArgsConstructor
    @Getter
    public enum PaymentMethod {
        MONEY,
        CARD,
        CARD_MONEY
    }

    @AllArgsConstructor
    @Getter
    public enum StatusEnum {
        HOA_DON_CHO,
        CHO_XAC_NHAN,
        XAC_NHAN,
        DANG_GIAO,
        DA_GIAO,
        HUY
    }
    @AllArgsConstructor
    @Getter
    public enum OrderType {
       DIRECT_TYPE,
        ONLINE
    }

}
