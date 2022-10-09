package com.example.website_ban_trang_suc_toan_huyen.DTO;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CategoryEntity;

import java.util.List;

public interface CategoryDTO {
    List<CategoryEntity> search(int page, int pageSize, String keyword, String sortBy);

    Long countCategory(int page, int pageSize, String keyword, String sortBy);
}
