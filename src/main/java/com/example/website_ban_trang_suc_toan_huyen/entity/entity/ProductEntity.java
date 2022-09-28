package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductClass extends BaseEntityEntity {
    @Id
    @GeneratedValue
    @Column(length = 36)
    private String productId;
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
    @Column
    private Integer amount;
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

}
