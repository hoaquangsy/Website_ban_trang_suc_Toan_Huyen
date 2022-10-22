package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.OrderDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.OrderRequest;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface OrderService {
    @Transactional
    OrderRequest saveOrder(OrderRequest orderRequest);

    OrderDTO update(UUID idOrder, OrderEntity.StatusEnum status);

    OrderDTO findOrder(UUID idOrder);

    Page<OrderDTO> getAllOrder(int page, int pageSize);

    Page<OrderDTO> findByUser(int page, int pageSize, UUID userId);

    PageDTO search(Integer pageIndex, Integer pageSize, String keyword,
                   OrderEntity.StatusEnum status,
                   OrderEntity.PaymentMethod payMethod, OrderEntity.OrderType orderType,
                   Instant startDate, Instant endDate,
                   BigDecimal startPrice, BigDecimal endPrice,UUID userId,
                   String sortBy);

}
