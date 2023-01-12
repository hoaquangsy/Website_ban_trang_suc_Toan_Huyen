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
    @Column(name = "product_id", length = 36)
    @Type(type = "uuid-char")
    private UUID productId;
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID categoryId;
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID vendorId;
    @Column(length = 36)
    private String code;
    @Column(length = 50)
    private String nameProduct;
    @Column
    private Boolean deleted;
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(length = 2000)
    private String note;
    @Column
    private BigDecimal salary;
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID accessoryId;
    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private ProductGender gender;
    @Column
    @Type(type = "uuid-char")
    private UUID eventId;
    @Column
    @Type(type = "uuid-char")
    private UUID materialId;

    public enum StatusEnum {
        ACTIVE,
        INACTIVE
    }
    public enum ProductGender {
        FEMALE,
        MALE,
        FEMALE_AND_MALE
    }
}
