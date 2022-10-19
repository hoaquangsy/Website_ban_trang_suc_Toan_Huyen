package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.VendorDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.VendorRequest;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public interface VendorService {
    VendorDto createVendor(VendorRequest newVendor);

    VendorDto updateVendor(UUID id, VendorRequest updateVendor);

    HttpStatus deleteVendor(UUID id);

    VendorDto getVendorById(UUID id);

    PageDTO getAllVendor(Integer page, Integer pageSize, String keyword, String sortBy);
    PageDTO autoComplete(Integer page, Integer pageSize, String keyword, String sortBy);


}
