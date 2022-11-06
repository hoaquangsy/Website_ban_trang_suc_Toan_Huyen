package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ExchangeSearchRequest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface ExchangeDAO {
    List<ExchangeEntity> search(ExchangeSearchRequest exchangeSearchRequest) throws ParseException;

    Long count(ExchangeSearchRequest exchangeSearchRequest) throws ParseException;
}
