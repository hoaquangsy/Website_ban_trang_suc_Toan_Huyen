package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table (name = "refund")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundEntity extends BaseEntity {
    @Id
    @Column(length = 36)
    private String refundId;
    @Column(length = 36)
    private String orderId;
    @Column(length = 36)
    private String productId;
    @Column
    private Boolean status;
    @Column(length = 255)
    private String reason;
    @Column(length = 255)
    private String note;
    @Column
    private Date create_at;
    @Column(length = 50)
    private String createBy;
    @Column
    private Date lastModifiedAt;
    @Column(length = 50)
    private String lastModifiedBy;

}
