package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table (name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @Column(length = 36,nullable = false)
    @Type(type = "uuid-char")
    private UUID userId;
    @Column(length = 50,nullable = false)
    private String fullname;
    @Column
    private LocalDate birthday;
    @Column(length = 12,nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(length = 30,nullable = false)
    private String username;
    @Column(nullable = false)
    private Boolean status;
    @Column(length = 30,nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean deleted;
    @Column(length = 100,nullable = false)
    private String note;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String imageUrl;
    @Getter
    @AllArgsConstructor
    public enum Gender{
        MALE,FEMALE
    }

    @Getter
    @AllArgsConstructor
    public enum Role{
        EMPLOYEE,CUSTOMER,MANAGER
    }


}
