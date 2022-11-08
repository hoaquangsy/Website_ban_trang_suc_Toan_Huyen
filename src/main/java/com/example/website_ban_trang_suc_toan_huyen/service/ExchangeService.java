package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ExchangeDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ExchangeRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ExchangeSearchRequest;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface ExchangeService {

    PageDTO search(ExchangeSearchRequest exchangeSearchRequest) throws ParseException;
    ExchangeDTO createExchange(ExchangeRequest request);
    ExchangeDTO updateExchange(UUID id,ExchangeRequest request);
    ExchangeDTO findById(UUID id);
    List<ExchangeEntity> getAllExchangeByTime(UUID orderId);

    List<ExchangeEntity> getAllExchangeByOrder(UUID orderId);
}
