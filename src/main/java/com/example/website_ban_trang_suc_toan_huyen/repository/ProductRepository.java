package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    ProductEntity findByNameProduct (String name);
    Optional<ProductEntity> findById (UUID id);

    ProductEntity findByProductIdAndStatus (UUID id, String status);

}
