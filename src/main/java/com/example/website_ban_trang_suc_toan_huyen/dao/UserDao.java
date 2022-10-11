package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CategoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;

import java.util.List;

public interface UserDao {

    List<UserEntity> search(String keyword, UserEntity.Role role, Integer page, Integer pageSize, String sortBy,Boolean status);

    Long count(String keyword, UserEntity.Role role, Integer page, Integer pageSize, String sortBy,Boolean status);

}
