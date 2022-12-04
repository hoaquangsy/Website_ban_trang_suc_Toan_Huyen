package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.VendorEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.WaitingProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.WaitingProductSearchRequest;

import java.text.ParseException;
import java.util.List;

public interface WaitingProductDAO {

    List<WaitingProductEntity> search(WaitingProductSearchRequest waitingProductSearchRequest) throws ParseException;

    Long count(WaitingProductSearchRequest waitingProductSearchRequest) throws ParseException;
}
