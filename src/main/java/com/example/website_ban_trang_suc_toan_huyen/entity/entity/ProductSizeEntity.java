package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product_size" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSizeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idProductSize;
    @Column
    private Integer sizId;
    @Column
    private Integer productId;
    @Column
    private Integer quantity;

}
