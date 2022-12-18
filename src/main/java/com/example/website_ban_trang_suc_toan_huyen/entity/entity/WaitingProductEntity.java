package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "waiting_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class   WaitingProductEntity extends BaseEntity {
    @Id
    @Type(type = "uuid-char")
    @Column
    private UUID id;
    @Column
    @Type(type = "uuid-char")
    private UUID productId;
    @Column
    private Integer quantity;
    @Type(type = "uuid-char")
    @Column
    private UUID sizeId;
    @Column
    private String note;
}
