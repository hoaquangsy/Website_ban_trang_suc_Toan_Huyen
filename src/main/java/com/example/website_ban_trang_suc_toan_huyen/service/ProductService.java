package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductRequest newProduct);

    ProductDto updateProduct(UUID id, ProductRequest productRequest);

    HttpStatus deleteProduct(UUID id);

    ProductDto getProductById(UUID id);

    Page<ProductDto> getAllProduct(int page, int pageSize);
}
