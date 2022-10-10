package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table (name = "material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialEntity extends BaseEntity {
    @Id
    @Column(name = "material_id",length = 36)
    @Type(type = "uuid-char")
    private UUID materialId;
    @Column
    private String materialName;
    @Column
    private BigDecimal purchasePrice;
    @Column
    private BigDecimal salePrice;
    @Column
    private String color;
    @Column
    @Enumerated(EnumType.STRING)
    private MaterialType type;
    @Column
    private Integer age;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Getter
    @AllArgsConstructor
    public enum StatusEnum {
        ACTIVE,
        INACTIVE
    }

    ;

    @Getter
    @AllArgsConstructor
    public enum MaterialType {
        VANG_TAY,VANG, BAC, DONG, BACH_KIM, KIM_CUONG

    }

}
