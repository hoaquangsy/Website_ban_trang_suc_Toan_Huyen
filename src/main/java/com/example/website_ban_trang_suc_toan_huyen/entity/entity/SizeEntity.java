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
@Table(name = "size" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SizeEntity extends  BaseEntity{
    @Id
    @Column(length = 36)
    @Type(type = "uuid-char")
    private UUID sizeId;
    @Column(length = 30)
    private String size;
    @Column
    private String description;
    @Column
    private Boolean deleted;

}
