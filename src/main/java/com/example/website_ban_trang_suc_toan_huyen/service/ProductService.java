package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductOrderDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductRequest newProduct);

    ProductDto updateProduct(UUID id, ProductRequest productRequest);

    HttpStatus deleteProduct(UUID id);

    ProductDto getProductById(UUID id);

    ProductDto lock(UUID id);

    ProductDto unlock(UUID id);

    Page<ProductDto> getAllProduct(int page, int pageSize);

    PageDTO search(Integer pageIndex,
                   Integer pageSize,
                   String keyword,
                   ProductEntity.StatusEnum status,
                   UUID materialId,
                   UUID vendorId,
                   UUID categoryId,
                   UUID accessoryId,
                   BigDecimal startPrice,
                   BigDecimal endPrice,
                   String sortBy,
                   ProductEntity.ProductGender gender);

    PageDTO searchV2(Integer pageIndex,
                     Integer pageSize,
                     String keyword,
                     ProductEntity.StatusEnum status,
                     List<UUID> materialId,
                     List<UUID> vendorId,
                     List<UUID> categoryId,
                     List<UUID> accessoryId,
                     BigDecimal startPrice,
                     BigDecimal endPrice,
                     String sortBy,
                     ProductEntity.ProductGender gender);

    PageDTO autoComplete(Integer pageIndex,
                         Integer pageSize,
                         String keyword,
                         ProductEntity.StatusEnum status,
                         UUID materialId,
                         UUID vendorId,
                         UUID categoryId,
                         UUID accessoryId,
                         BigDecimal startPrice,
                         BigDecimal endPrice,
                         String sortBy,
                         ProductEntity.ProductGender gender);

    List<ProductOrderDto> getProductOrder();

    List<ProductDto> getProductTrending();
}
