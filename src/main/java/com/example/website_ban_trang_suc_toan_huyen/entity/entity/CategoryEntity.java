package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    @Id
    @Column(length = 36)
    private String categoryId;
    @Column(length = 30)
    private String name;
    @Column(length = 50)
    private String description;
    @Column(length = 50)
    private String createBy;
    private Date createAt;
    private String updateBy;
    private Date updateAt;
    private Boolean status;
    
}
