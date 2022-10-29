package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product_size" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSizeEntity extends  BaseEntity {
    @Id
    @Type(type = "uuid-char")
    @Column
    private UUID idProductSize;
    @Column
    @Type(type = "uuid-char")
    private UUID sizeId;
    @Column
    @Type(type = "uuid-char")
    private UUID productId;
    @Column
    private Integer quantity;
    @Column
    private float weight;
    @Column
    private BigDecimal purchasePrice;
    @Column
    private BigDecimal salePrice;
    @Column
    private Boolean deleted;

}
