package com.example.website_ban_trang_suc_toan_huyen.dao;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.EventEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.SizeEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.EventSearchRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.SizeSearchRequest;

import java.text.ParseException;
import java.util.List;

public interface SizeDAO {
    List<SizeEntity> search(SizeSearchRequest sizeSearchRequest) ;

    Long count(SizeSearchRequest sizeSearchRequest) ;
}
