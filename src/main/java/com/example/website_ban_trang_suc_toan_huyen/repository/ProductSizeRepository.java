package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSizeEntity,UUID> {
    ProductSizeEntity findByProductIdAndSizId (UUID productId, UUID sizeId);
}
