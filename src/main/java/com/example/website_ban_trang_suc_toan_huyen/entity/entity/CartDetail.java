package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetail {
    @Id
    @Column(length = 36)
    private String cartId;
    @Column(length = 36)
    private String productId;
    @Column
    private Integer amount;
    @Column
    private BigDecimal total;

}
