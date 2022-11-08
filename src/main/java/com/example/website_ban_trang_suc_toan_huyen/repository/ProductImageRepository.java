package com.example.website_ban_trang_suc_toan_huyen.repository;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, UUID> {

    @Query("select  p from ProductImageEntity p where p.productId = :id and p.deleted = false")
    List<ProductImageEntity> findByProductId(UUID id);

}
