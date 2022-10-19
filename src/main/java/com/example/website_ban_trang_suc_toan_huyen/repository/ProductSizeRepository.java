package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSizeEntity,UUID> {
    ProductSizeEntity findByProductIdAndSizeId (UUID productId, Integer sizeId);
    @Query("select  p from ProductSizeEntity p where p.productId = :id and p.deleted = false")
    List<ProductSizeEntity> findByProductId(UUID id);
}
