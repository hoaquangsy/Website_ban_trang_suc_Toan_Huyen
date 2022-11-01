package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ExchangeRequest;

import java.util.List;
import java.util.UUID;

public interface ExchangeService {
    List<ExchangeEntity> createExchange(ExchangeRequest request);

    List<ExchangeEntity> getAllExchangeByTime(UUID orderId);

    List<ExchangeEntity> getAllExchangeByOrder(UUID orderId);
}
