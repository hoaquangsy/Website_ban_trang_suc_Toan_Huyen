package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.AccessoryDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.AccessoryRequest;
import com.example.website_ban_trang_suc_toan_huyen.support.enums.AccessoryStatus;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccessoryService {
    AccessoryDTO create(AccessoryRequest request);

    AccessoryDTO update(AccessoryRequest request, UUID id);

    HttpStatus delete(UUID id);

    AccessoryDTO getById(UUID id);

    PageDTO search(String keyword, Integer pageIndex, Integer pageSize,
                   AccessoryStatus status, String sortBy);
    HttpStatus active(UUID id);
    HttpStatus inactive(UUID id);
    HttpStatus draft(UUID id);
}
