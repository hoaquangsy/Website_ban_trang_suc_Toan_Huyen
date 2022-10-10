package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CategoryEntity;

import java.util.List;

public interface CategoryDao {
    List<CategoryEntity> search(Integer page, Integer pageSize, String keyword, String sortBy);

    Long countCategory(Integer page, Integer pageSize, String keyword, String sortBy);
}
