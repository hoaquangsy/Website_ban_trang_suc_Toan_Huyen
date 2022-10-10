package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CategoryDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CategoryRequest newCategory);

    CategoryDto updateCategory(UUID id, CategoryRequest dto);

    CategoryDto delete(UUID id);
    CategoryDto getCategoryById(UUID id);

    Page<CategoryDto> getAllCategory(int page, int pageSize);

    PageDTO search(Integer page, Integer pageSize,String keyword,String sortBy);


}
