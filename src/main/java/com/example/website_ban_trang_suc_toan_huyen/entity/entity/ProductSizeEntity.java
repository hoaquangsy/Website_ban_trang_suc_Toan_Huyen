package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "product_size" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSizeEntity {
    @Id
    @Type(type = "uuid-char")
    @Column
    private UUID idProductSize;
    @Column
    private Integer sizId;
    @Column
    private UUID productId;
    @Column
    private Integer quantity;

}
