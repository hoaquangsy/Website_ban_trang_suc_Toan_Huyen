package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accessory")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccessoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer accessoryId;
    @Column(length = 30)
    private String name;
    @Column
    private BigDecimal price;
    @Column(length = 50)
    private String description;
    
}
