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
@Table(name = "exchange_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDetailEntity extends BaseEntity{
    @Id
    @Column
    @Type(type = "uuid-char")
    private UUID id;
    @Column
    @Type(type = "uuid-char")
    private UUID exchangeId;
    @Column
    @Type(type = "uuid-char")
    private UUID productId;
    @Column
    @Type(type = "uuid-char")
    private UUID sizeId;

    @Column
    private Integer quantity;

}
