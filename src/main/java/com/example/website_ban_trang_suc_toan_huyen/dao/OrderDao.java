package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OrderDao {
    List<OrderEntity> search(Integer pageIndex, Integer pageSize, String keyword, OrderEntity.StatusEnum status, OrderEntity.PaymentMethod payMethod, OrderEntity.OrderType orderType, String startDate, String endDate, BigDecimal startPrice, BigDecimal endPrice, UUID userId, String sortBy,Boolean isRepurchase) throws ParseException;

    Long count(Integer pageIndex, Integer pageSize, String keyword, OrderEntity.StatusEnum status, OrderEntity.PaymentMethod payMethod, OrderEntity.OrderType orderType, String startDate, String endDate, BigDecimal startPrice, BigDecimal endPrice,UUID userId, String sortBy,Boolean isRepurchase) throws ParseException;
}
