package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table (name = "material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer materialId;
    @Column
    private String materialName;
    @Column
    private BigDecimal purchasePrice;
    @Column
    private BigDecimal salePrice;
    @Column
    private String color;
    @Column
    private String type;
    @Column
    private Integer age;
    @Column
    private Integer status;

    @Getter
    @AllArgsConstructor
    public enum StatusEnum{
        ACTIVE(1),
        INACTIVE(0);

        private final int code;
    }

}
