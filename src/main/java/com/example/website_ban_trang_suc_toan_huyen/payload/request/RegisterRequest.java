package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import lombok.Data;

@Data
public class RegisterRequest extends LoginRequest {
    private String passwordConfirm;
    private UserEntity.Gender gender;

}


