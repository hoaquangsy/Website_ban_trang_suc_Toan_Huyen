package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductPropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductPropertyRepository extends JpaRepository<ProductPropertyEntity, UUID> {
    @Query("select  p from ProductPropertyEntity p where p.productId = :id and p.deleted = false")
    List<ProductPropertyEntity> findByProductId(UUID id);
}
