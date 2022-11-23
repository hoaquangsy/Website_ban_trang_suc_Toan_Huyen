package com.example.website_ban_trang_suc_toan_huyen.repository;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.WaitingProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WaitingProductRepository extends JpaRepository<WaitingProductEntity, UUID> {
    List<WaitingProductEntity> findAllByIdIn(List<UUID> id);

    WaitingProductEntity findAllByProductIdAndSizeId(UUID productId, UUID sizeId);
}
