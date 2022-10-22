package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface OrderDao {
    List<OrderEntity> search(Integer pageIndex, Integer pageSize, String keyword, OrderEntity.StatusEnum status, OrderEntity.PaymentMethod payMethod, OrderEntity.OrderType orderType, Instant startDate, Instant endDate, BigDecimal startPrice, BigDecimal endPrice, UUID userId, String sortBy);

    Long count(Integer pageIndex, Integer pageSize, String keyword, OrderEntity.StatusEnum status, OrderEntity.PaymentMethod payMethod, OrderEntity.OrderType orderType, Instant startDate, Instant endDate, BigDecimal startPrice, BigDecimal endPrice,UUID userId, String sortBy);
}
