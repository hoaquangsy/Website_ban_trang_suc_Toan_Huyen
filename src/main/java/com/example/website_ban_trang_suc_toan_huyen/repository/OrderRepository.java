package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    Page<OrderEntity> findAllByUserId(UUID uuid,Pageable pageable);

    OrderEntity findByIdAndStatus(UUID uuid, OrderEntity.StatusEnum status);

    List<OrderEntity> findByUserId(UUID userId);

    List<OrderEntity> findByUserIdAndStatus(UUID userId, OrderEntity.StatusEnum status);
}
