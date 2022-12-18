package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.WaitingProductDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.WaitingProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.WaitingProductSearchRequest;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface WaitingProductService {
    List<WaitingProductDTO> findAllByOrderId(UUID orderId);

    WaitingProductDTO createWaitingProduct(WaitingProductRequest waitingProductRequest);

    WaitingProductDTO updateWaitingProduct(UUID id,WaitingProductRequest waitingProductRequest);

    WaitingProductDTO sendProduct(UUID id);

    List<WaitingProductDTO> findAll();

    PageDTO search(WaitingProductSearchRequest waitingProductSearchRequest) throws ParseException;
}
