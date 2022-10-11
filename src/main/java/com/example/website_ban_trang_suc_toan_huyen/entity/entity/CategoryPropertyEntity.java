package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "category_property")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPropertyEntity extends BaseEntity {
    @Id
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID categoryPropertyId;
    @Column(length = 30)
    private String name;
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID categoryId;
}
