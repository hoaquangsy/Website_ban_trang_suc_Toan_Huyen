package com.example.website_ban_trang_suc_toan_huyen.service;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.SizeDto;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.SizeRequest;

import java.util.List;
import java.util.UUID;

public interface SizeService {

    SizeDto create(SizeRequest sizeRequest);

    SizeDto update(UUID id,SizeRequest sizeRequest);

    SizeDto delete(UUID id);

    SizeDto getById(UUID id);

    List<SizeDto> getAllSize();

    List<SizeDto> getByProductId(UUID productId);



}
