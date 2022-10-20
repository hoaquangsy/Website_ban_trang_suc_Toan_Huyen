package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.OrderDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface OrderService {
    @Transactional
    OrderRequest saveOrder(OrderRequest orderRequest);

    OrderDTO update(UUID idOrder, Integer status);

    OrderDTO findOrder(UUID idOrder);

    Page<OrderDTO> getAllOrder(int page, int pageSize);

    Page<OrderDTO> findByUser(int page, int pageSize, UUID userId);
}
