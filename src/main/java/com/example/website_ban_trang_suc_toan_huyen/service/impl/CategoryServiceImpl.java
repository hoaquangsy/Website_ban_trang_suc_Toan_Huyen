package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CategoryDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CategoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.repository.CategoryRepository;
import com.example.website_ban_trang_suc_toan_huyen.security.UserDetailsImpl;
import com.example.website_ban_trang_suc_toan_huyen.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private  ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto newCategory){
        CategoryEntity category = modelMapper.map(newCategory, CategoryEntity.class);
        UserDetailsImpl currentUser = new UserDetailsImpl();
//                = CurrentUserUtils.getCurrentUserDetails();
//        category.setCreateAt(new Date());
//        category.setCreateBy(currentUser.getId()+"");
//        category.setUpdateAt(new Date());
//        category.setUpdateBy(currentUser.getId()+"");
        return modelMapper.map(categoryRepository.save(category),CategoryDto.class);
    }
    @Override
    public CategoryDto updateCategory(String id, CategoryDto dto){
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Category not found")
        );
        modelMapper.map(dto,category);
        UserDetailsImpl currentUser = new UserDetailsImpl();
//                = CurrentUserUtils.getCurrentUserDetails();
//        category.setUpdateAt(new Date());
//        category.setUpdateBy(currentUser.getId()+"");
        return modelMapper.map(categoryRepository.save(category),CategoryDto.class);
    }
    @Override
    public CategoryDto getCategoryById(String id){
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Category not found")
        );
        return modelMapper.map(category,CategoryDto.class);
    }
    @Override
    public Page<CategoryDto> getAllCategory(int page, int pageSize){
        Page<CategoryEntity> categoryEntityPage=categoryRepository.findAll(PageRequest.of(page,pageSize));
        if (categoryEntityPage.getTotalElements()>0){
            return categoryEntityPage.map(categoryEntity -> modelMapper.map(categoryEntity,CategoryDto.class));
        }
        throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Categories not exist");
    }
}
