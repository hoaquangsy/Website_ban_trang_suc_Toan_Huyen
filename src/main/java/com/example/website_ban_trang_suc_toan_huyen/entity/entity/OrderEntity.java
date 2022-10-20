package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private String userId;
    @Column
    private Date dayTrading;
    @Column
    private BigDecimal customerMoney;
    @Column
    private String paymentMethod;
    @Column
    private Boolean purchase;
    @Column
    private BigDecimal transportFee;
    @Column
    private BigDecimal deposit;
    @Column
    private BigDecimal total;
    @Column
    private Integer status;
    @Column
    private Boolean type;

    @Column
    private UUID eventId;

    @AllArgsConstructor
    @Getter
    public enum PaymentMethod {
        MONEY,
        CARD;
    }
    @AllArgsConstructor
    @Getter
    public enum StatusEnum {
        OPEN(1),
        CLOSE(2);
        private Integer code;

    }


}
