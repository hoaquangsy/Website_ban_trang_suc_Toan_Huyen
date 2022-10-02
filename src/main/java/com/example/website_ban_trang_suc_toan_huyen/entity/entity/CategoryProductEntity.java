package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductEntity {
    @Id
    @Column(length = 36)
    private String categoryProductId;
    @Column(length = 36)
    private String productId;
    @Column(length = 36)
    private String categoryId;

    
}
