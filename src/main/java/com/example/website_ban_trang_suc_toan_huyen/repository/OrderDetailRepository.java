package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, UUID> {
    @Query(" select o from OrderDetailEntity o where o.orderId = :id")
    List<OrderDetailEntity> findByOrderId(UUID id);

    @Query(" select o from OrderDetailEntity o where o.orderId = :orderId and o.productId in :productId")
    List<OrderDetailEntity> findByOrderIdAndProductIdIn(UUID orderId, List<UUID> productId);

    @Query(value = "SELECT * FROM `order_detail` dt JOIN Orders o ON o.id=dt.order_id WHERE o.status='DA_GIAO' GROUP BY dt.order_id,dt.product_id \n" +
            "ORDER BY COUNT(dt.quantity) ",nativeQuery = true)
    List<OrderDetailEntity> findByProductTrending();

     Optional<OrderDetailEntity> findByOrderIdAndProductIdAndSizeId(UUID orderId, UUID productId,UUID sizeId);
}
