package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.MaterialDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.MaterialRequest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.UUID;

public interface MaterialService {
    MaterialDto createMaterial(MaterialRequest request);

    MaterialDto updateMaterial(MaterialRequest request, UUID id);

    MaterialDto lock(UUID id);

    MaterialDto getById(UUID id);

    MaterialDto unlock(UUID id);

    void deleteMaterial(UUID id);

    Page<MaterialDto> getAllMaterial(int page, int pageSize);

    PageDTO search(String keyword, Integer pageIndex, Integer pageSize,
                   MaterialEntity.MaterialType type, MaterialEntity.StatusEnum status,
                   BigDecimal startPrice,BigDecimal endPrice,String sortBy);
    PageDTO autoComplete(String keyword, Integer pageIndex, Integer pageSize,
                   MaterialEntity.MaterialType type, MaterialEntity.StatusEnum status,
                   BigDecimal startPrice,BigDecimal endPrice,String sortBy);
}
