package com.example.website_ban_trang_suc_toan_huyen.entity.dto;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID userId;

    private String fullName;

    private LocalDate birthday;

    private String phoneNumber;

    private String maNV;

    private UserEntity.Gender gender;

    private String userName;
    @JsonIgnore
    private String password;

    private Boolean deleted;

    private String note;

    private String cccd;

    private UserEntity.Role role;

    private String address;

    private String email;

    private Boolean status;

    private Instant createAt;

    private String createBy;

    private String imageUrl;

    private Instant lastModifiedAt;

    private String lastModifiedBy;

}
