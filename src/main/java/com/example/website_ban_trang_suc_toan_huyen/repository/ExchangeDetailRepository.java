package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeDetailEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeDetailRepository extends JpaRepository<ExchangeDetailEntity,UUID> {
    @Query("select  obj from ExchangeDetailEntity obj where obj.productId = :productId and obj.sizeId = :sizeId and obj.exchangeId in (select e.id from ExchangeEntity e where e.orderId = :orderId)")
    Optional<ExchangeDetailEntity> findByOrderAndProduct(UUID productId,UUID sizeId,UUID orderId);

    List<ExchangeDetailEntity> findByExchangeId(UUID exchangeId);
}
