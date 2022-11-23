package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.WaitingProductDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.WaitingProductRequest;

import java.util.List;
import java.util.UUID;

public interface WaitingProductService {
    List<WaitingProductDTO> findAllByOrderId(UUID orderId);

    WaitingProductDTO createWaitingProduct(WaitingProductRequest waitingProductRequest);

    WaitingProductDTO sendProduct(WaitingProductRequest waitingProductRequest);

    List<WaitingProductDTO> findAll();
}
