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
@Table(name = "cart_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailEntity extends BaseEntity{
    @Id
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID id;
    @Column
    private Integer amount;

    @Column(name = "product_id",length = 36)
    @Type(type = "uuid-char")
    private UUID productId;

    @Column(name = "cart_id",length = 36)
    @Type(type = "uuid-char")
    private UUID cartId;

    @Column(name = "size_id",length = 36)
    @Type(type = "uuid-char")
    private UUID sizeId;
}
