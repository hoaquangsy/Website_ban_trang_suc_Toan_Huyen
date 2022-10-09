package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.MaterialDto;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.MaterialRequest;
import org.springframework.data.domain.Page;

public interface MaterialService {
    MaterialDto createMaterial(MaterialRequest request);

    MaterialDto updateMaterial(MaterialRequest request, Integer id);

    void deleteMaterial(Integer id);

    Page<MaterialDto> getAllMaterial(int page, int pageSize);
}
