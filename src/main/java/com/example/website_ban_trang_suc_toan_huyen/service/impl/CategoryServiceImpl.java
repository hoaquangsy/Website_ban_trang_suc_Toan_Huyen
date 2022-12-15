package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.CategoryDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CategoryDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CategoryPropertyDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CategoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CategoryPropertyEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CategoryRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.CategoryPropertyRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.CategoryRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private  ModelMapper modelMapper;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryPropertyRepository categoryPropertyRepository;

    @Autowired
    private HttpSession session;

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryRequest newCategory) {
        this.checkProperties(newCategory.getProperties());
        CategoryEntity category = modelMapper.map(newCategory, CategoryEntity.class);
        category.setCategoryId(UUID.randomUUID());
        category.setDeleted(Boolean.FALSE);
        return getCategoryDto(newCategory, category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(UUID id, CategoryRequest dto) {
        this.checkProperties(dto.getProperties());
        this.getCategoryById(id);
        CategoryEntity category = this.modelMapper.map(dto,CategoryEntity.class);
        category.setCategoryId(id);
        category.setDeleted(Boolean.FALSE);
        category.setLastModifiedBy(dto.getLastModifiedBy());
        List<UUID> categoryPropertyEntities =
                this.categoryPropertyRepository.findCategoryPropertyEntitiesByCategoryId(id);
        this.categoryPropertyRepository.deleteAllById(categoryPropertyEntities);
        return getCategoryDto(dto, category);
    }

    @Override
    public CategoryDto delete(UUID id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Category not found"));
        category.setDeleted(Boolean.TRUE);
        this.categoryRepository.save(category);
        return this.modelMapper.map(category,CategoryDto.class);
    }

    private CategoryDto getCategoryDto(CategoryRequest dto, CategoryEntity category) {
        categoryRepository.save(category);
        List<CategoryPropertyEntity> categoryPropertyEntities = dto.getProperties().stream()
                .map(s -> new CategoryPropertyEntity(UUID.randomUUID(), s, category.getCategoryId()))
                .collect(Collectors.toList());
        this.categoryPropertyRepository.saveAll(categoryPropertyEntities);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(UUID id) {
        CategoryEntity category = categoryRepository.findId(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Category not found")
        );
        List<CategoryPropertyDTO> propertyDTOS =
                this.categoryPropertyRepository.findByCategoryId(category.getCategoryId())
                        .stream().map(categoryPropertyEntity -> modelMapper.map(categoryPropertyEntity,CategoryPropertyDTO.class))
                        .collect(Collectors.toList());
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        categoryDto.setProperties(propertyDTOS);
        return categoryDto;
    }

    @Override
    public Page<CategoryDto> getAllCategory(int page, int pageSize) {
        Page<CategoryEntity> categoryEntityPage = categoryRepository.findAll(PageRequest.of(page, pageSize));
        if (categoryEntityPage.getTotalElements() > 0) {
            return categoryEntityPage.map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDto.class));
        }
        throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Categories not exist");
    }

    @Override
    public PageDTO search(Integer page, Integer pageSize, String keyword, String sortBy) {
        List<CategoryEntity> categoryEntityList = this.categoryDao.search(page, pageSize, keyword, sortBy);
        List<CategoryDto> categoryDtos = categoryEntityList.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDto.class)).collect(Collectors.toList());
       categoryDtos.forEach(categoryDto -> {
           List<CategoryPropertyDTO> propertyDTOS =
                   this.categoryPropertyRepository.findByCategoryId(categoryDto.getCategoryId())
                           .stream().map(categoryPropertyEntity -> modelMapper.map(categoryPropertyEntity,CategoryPropertyDTO.class))
                           .collect(Collectors.toList());
           categoryDto.setProperties(propertyDTOS);
       });
        Long count = this.categoryDao.countCategory(page, pageSize, keyword, sortBy);
        return new PageDTO<>(categoryDtos, page, pageSize, count);
    }

    public PageDTO autoComplete(Integer page, Integer pageSize, String keyword, String sortBy) {
        Long count = this.categoryDao.countCategory(page, pageSize, keyword, sortBy);
        pageSize = Integer.parseInt(count.toString());
        List<CategoryEntity> categoryEntityList = this.categoryDao.search(page, pageSize, keyword, sortBy);
        List<CategoryDto> categoryDtos = categoryEntityList.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDto.class)).collect(Collectors.toList());
        return new PageDTO<>(categoryDtos, page, pageSize, count);
    }

    @Override
    public List<CategoryPropertyDTO> getProperties(UUID id) {
        return this.categoryPropertyRepository.findByCategoryId(id).stream().map(categoryPropertyEntity ->  this.modelMapper.map(categoryPropertyEntity,CategoryPropertyDTO.class)).collect(Collectors.toList());
    }

    private void checkProperties(List<String> properties) {
        Set<String> propertiesSet = new HashSet<>();
        propertiesSet.addAll(properties);
        if (properties.size() > propertiesSet.size()) {
            throw new NotFoundException(HttpStatus.NOT_ACCEPTABLE.value(), "Thuộc tính bị trùng lặp");
        }
        List<String>  productProperty = List.of
                ("productId","accessory","amount","category",
                        "code","createAt","createBy","LastmodifiedAt","LastModifiedBy");
        properties.forEach(s -> {
           String p =  productProperty.stream().filter(s1 -> s1.equalsIgnoreCase(s)).findFirst().orElse(null);;
           if(Objects.nonNull(p)){
               throw new NotFoundException(HttpStatus.NOT_ACCEPTABLE.value(),"Thuộc tính "+ p + " tồn tại trong Sản phẩm ");
           }
        });

    }
}
