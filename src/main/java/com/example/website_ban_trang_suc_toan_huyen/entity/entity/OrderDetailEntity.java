package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table (name = "order_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEntity {
    @Id
    @Type(type = "uuid-char")
    @Column
    private UUID id;
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID productId;
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID orderId;
    @Column
    private Integer quantity;
    @Column
    private BigDecimal price;
    @Column
    private BigDecimal discount;
    @Column
    private BigDecimal total;
    @Column
    @Type(type = "uuid-char")
    private UUID sizeId;

}
