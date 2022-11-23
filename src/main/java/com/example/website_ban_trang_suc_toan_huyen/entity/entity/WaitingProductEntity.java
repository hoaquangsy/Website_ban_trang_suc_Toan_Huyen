package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "waiting_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaitingProductEntity extends BaseEntity {
    @Id
    @Type(type = "uuid-char")
    private UUID Id;
    @Type(type = "uuid-char")
    private UUID productId;
    private String name;
    private Integer quantity;
    @Type(type = "uuid-char")
    private UUID sizeId;
    private String note;
}
