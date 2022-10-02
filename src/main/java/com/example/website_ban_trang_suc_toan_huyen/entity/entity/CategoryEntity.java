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
    @Column
    private Date createAt;
    @Column(length = 50)
    private String createBy;
    @Column
    private Date updateAt;
    @Column(length = 50)
    private String updateBy;
    @Column
    private Boolean status;


}
