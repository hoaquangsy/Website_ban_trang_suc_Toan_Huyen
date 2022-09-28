package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryClass {
    @Id
    @Column(length = 36)
    private String accessoryId;
    @Column(length = 30)
    private String name;
    @Column(length = 30)
    private String price;
    @Column(length = 50)
    private String description;
    
}
