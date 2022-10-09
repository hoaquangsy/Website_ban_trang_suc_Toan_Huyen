package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.VendorEntity;

import java.util.List;

public interface VendorDao {
    List<VendorEntity> search(Integer page, Integer pageSize, String keyword, String sortBy);
    Long count(Integer page, Integer pageSize, String keyword, String sortBy);
}
