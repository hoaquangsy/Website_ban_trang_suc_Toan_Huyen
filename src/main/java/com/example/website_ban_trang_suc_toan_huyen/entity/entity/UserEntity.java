package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table (name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(length = 36)
    private String userId;
    @Column(length = 50)
    private String fullname;
    @Column
    private Date birthday;
    @Column(length = 50)
    private String phoneNumber;
    @Column
    private Boolean gender;
    @Column(length = 30)
    private String username;
    @Column(length = 30)
    private String password;
    @Column
    private Boolean status;
    @Column(length = 100)
    private String note;
    @Column
    private Date createAt;
    @Column(length = 50)
    private String createBy;
    @Column
    private Date lastModifiedAt;
    @Column(length = 50)
    private String lastModifiedBy;

}
