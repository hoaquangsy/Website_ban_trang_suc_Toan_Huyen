package com.example.website_ban_trang_suc_toan_huyen.entity.dto;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private UserEntity.Role role;
}
