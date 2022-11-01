package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExchangeRepository extends JpaRepository<ExchangeEntity,String> {
    @Query("select  obj from ExchangeEntity obj where obj.orderId=?1 order by obj.time")
    List<ExchangeEntity> findByOrder(UUID orderId);
    List<ExchangeEntity> findByOrderIdAndTime(UUID orderId,Integer time);
    List<ExchangeEntity> findByOrderIdAndProductIdIn(UUID orderId, List<UUID> productId);

}
