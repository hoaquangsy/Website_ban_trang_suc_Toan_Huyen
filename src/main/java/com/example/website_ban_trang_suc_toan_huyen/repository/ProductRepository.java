package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    @Query("select  p from ProductEntity p where  p.nameProduct = :name and p.deleted = false")
    Optional<ProductEntity> findByNameProduct (String name);

    @Query("select  p from ProductEntity p where  p.productId = :id and p.deleted = false")
    Optional<ProductEntity> findID(UUID id);

//    @Query("select  p from ProductEntity p where  p.productId = :id and p.status = status")
    ProductEntity findByProductIdAndStatus (UUID productId, ProductEntity.StatusEnum status);
}
