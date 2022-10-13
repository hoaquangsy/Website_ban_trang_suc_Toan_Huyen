package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import com.example.website_ban_trang_suc_toan_huyen.support.enums.AccessoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accessory")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccessoryEntity extends BaseEntity {
    @Id
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID accessoryId;
    @Column(length = 30)
    private String name;
    @Column
    private BigDecimal price;
    @Column(length = 30)
    private String color;
    @Column(length = 50)
    private String description;
    @Column
    @Enumerated(EnumType.STRING)
    private AccessoryStatus status;
    @Column
    private Boolean deleted;
    
}
