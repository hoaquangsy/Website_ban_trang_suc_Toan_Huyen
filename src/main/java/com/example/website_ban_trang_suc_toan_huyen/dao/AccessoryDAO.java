package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.AccessoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.support.enums.AccessoryStatus;

import java.math.BigDecimal;
import java.util.List;

public interface AccessoryDAO {
    List<AccessoryEntity> search(Integer page, Integer pageSize, String keyword, String sortBy,
                                 AccessoryStatus status,
                                 BigDecimal startPrice, BigDecimal endPrice);
    Long count(Integer page, Integer pageSize, String keyword, String sortBy,
               AccessoryStatus status,
               BigDecimal startPrice, BigDecimal endPrice);
}
