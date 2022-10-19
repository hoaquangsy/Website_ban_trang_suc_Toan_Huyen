package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;

import java.math.BigDecimal;
import java.util.List;

public interface MaterialDao {
    List<MaterialEntity> search(String keyword, Integer pageIndex, Integer pageSize,
                                MaterialEntity.MaterialType type, MaterialEntity.StatusEnum status,
                                BigDecimal startPrice, BigDecimal endPrice,String sortBy);

    Long count(String keyword, Integer pageIndex, Integer pageSize,
                       MaterialEntity.MaterialType type, MaterialEntity.StatusEnum status,
                       BigDecimal startPrice,BigDecimal endPrice,String sortBy);
}
