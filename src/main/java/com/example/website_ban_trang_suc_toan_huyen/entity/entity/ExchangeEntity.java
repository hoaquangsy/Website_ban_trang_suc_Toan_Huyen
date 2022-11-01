package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table (name = "exchange")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeEntity extends BaseEntity {
    @Id
    @Column
    private UUID id;
    @Column
    private UUID orderId;
    @Column
    private UUID productId;
    @Column
    private Boolean status;
    @Column(length = 255)
    private String reason;
    @Column(length = 255)
    private String note;
    private int time;

    public enum StatusEnum {
        ACTIVE,
        INACTIVE
    }
}
