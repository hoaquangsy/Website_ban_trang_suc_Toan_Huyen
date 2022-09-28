package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table (name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @Column(length = 36)
    private String orderId;
    @Column(length = 36)
    private String userId;
    @Column
    private Date dayTrading;
    @Column
    private BigDecimal customerMoney;
    @Column
    private Boolean paymentMethod;
    @Column
    private Boolean purchase;
    @Column
    private BigDecimal transportFee;
    @Column
    private BigDecimal deposit;
    @Column
    private BigDecimal total;
    @Column
    private Boolean status;
    @Column
    private Boolean type;

}
