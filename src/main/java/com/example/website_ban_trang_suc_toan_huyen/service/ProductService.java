package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDto createProduct(ProductDto newProduct);

    ProductDto updateProduct(String id, ProductDto dto);

    ProductDto getProductById(String id);

    Page<ProductDto> getAllProduct(int page, int pageSize);
}
