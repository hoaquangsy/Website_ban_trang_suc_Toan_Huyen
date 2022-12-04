package com.example.website_ban_trang_suc_toan_huyen.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    private String fullName;
    @Column
    private LocalDate birthday;
    @Column(length = 12)
    private String phoneNumber;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(length = 30)
    private String userName;
    @Column(nullable = false)
    private Boolean status;
    @Column(length = 200)
    private String password;
    @Column(length = 20)
    private String cccd;
    @Column
    private String maNV;
    @Column(nullable = false)
    private Boolean deleted;
    @Column(length = 100)
    private String note;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column
    private String address;
    @Column()
    private String email;
    @Column()
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
