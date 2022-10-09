package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CategoryDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import org.springframework.data.domain.Page;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto newCategory);

    CategoryDto updateCategory(String id, CategoryDto dto);

    CategoryDto getCategoryById(String id);

    Page<CategoryDto> getAllCategory(int page, int pageSize);

    PageDTO search(Integer page, Integer pageSize,String keyword,String sortBy);


}
