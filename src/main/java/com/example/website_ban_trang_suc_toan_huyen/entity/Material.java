package com.example.website_ban_trang_suc_toan_huyen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    @Id
    @Column(length = 36)
    private String materialId;
    @Column(length = 36)
    private String materialName;
    @Column(length = 50)
    private String description;

}
