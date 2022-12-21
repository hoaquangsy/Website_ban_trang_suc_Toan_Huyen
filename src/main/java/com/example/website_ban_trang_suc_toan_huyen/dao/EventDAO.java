package com.example.website_ban_trang_suc_toan_huyen.dao;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.EventEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.EventSearchRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ExchangeSearchRequest;

import java.text.ParseException;
import java.util.List;

public interface EventDAO {
    List<EventEntity> search(EventSearchRequest EventSearchRequest) throws ParseException;

    Long count(EventSearchRequest EventSearchRequest) throws ParseException;
}
