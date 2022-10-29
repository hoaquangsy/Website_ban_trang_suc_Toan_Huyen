package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductDao {
    List<ProductEntity> search(Integer pageIndex,
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
                               ProductEntity.ProductGender gender
                               );

    Long count(Integer pageIndex,
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
}
