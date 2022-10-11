package com.example.website_ban_trang_suc_toan_huyen.payload.response;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
public class UserResponse {

    private List<UserEntity> userEntity;
    public String message;

    public List<UserEntity> getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(List<UserEntity> userEntity) {
        this.userEntity = userEntity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
