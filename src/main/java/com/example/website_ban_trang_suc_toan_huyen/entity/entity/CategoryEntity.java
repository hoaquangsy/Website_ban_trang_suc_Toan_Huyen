package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "category")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity extends BaseEntity {
    @Id
    @Column(name = "category_id",length = 36)
    @Type(type = "uuid-char")
    private UUID categoryId;

    @Column(length = 30)
    private String name;

    @Column(length = 255)
    private String description;

    @Column
    private Boolean deleted;

}
