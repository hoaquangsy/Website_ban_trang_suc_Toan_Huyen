package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity extends BaseEntity {
    @Id
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID eventId;
    @Column
    private String code;
    @Column
    private Boolean deleted ;
    @Column
    private String name;
    @Column
    private Integer discount;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column
    private String description;
}
