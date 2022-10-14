package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends BaseEntity {
    @Id
    @Type(type = "uuid-char")
    @Column
    private UUID productId;
    @Column(length = 36)
    private UUID categoryId;
    @Column(length = 36)
    private UUID vendorId;
    @Column(length = 36)
    private String code;
    @Column(length = 50)
    private String nameProduct;
    @Column
    private float weight;
    @Column
    private BigDecimal purchasePrice;
    @Column
    private BigDecimal salePrice;
    @Column(length = 50)
    private String status;
    @Column(length = 50)
    private String note;
    @Column
    private BigDecimal salary;
    @Column(length = 36)
    private String accessoryId;
    @Column(length = 30)
    private String gender;
    @Column
    private UUID eventId;
    @Column
    private UUID materialId;

    public enum StatusEnum {
        ACTIVE,
        INACTIVE
    }
}
