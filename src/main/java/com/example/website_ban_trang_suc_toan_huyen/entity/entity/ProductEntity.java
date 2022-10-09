package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table (name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer productId;
    @Column(length = 36)
    private String categoryId;
    @Column(length = 36)
    private String vendorId;
    @Column(length = 36)
    private String code;
    @Column(length = 50)
    private String nameProduct;
    @Column
    private float weight;
    @Column
    private BigDecimal purchasePrice;
    @Column
    private BigDecimal saleprice;
    @Column(length = 50)
    private String status;
    @Column(length = 50)
    private String note;
    @Column
    private Date createAt;
    @Column
    private Date lastModifiedAt;
    @Column(length = 50)
    private String lastModifiedBy;
    @Column
    private BigDecimal salary;
    @Column(length = 36)
    private String accessoryId;
    @Column(length = 30)
    private String gender;
}
