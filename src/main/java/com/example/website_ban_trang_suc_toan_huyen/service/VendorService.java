package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.VendorDto;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.VendorRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

public interface VendorService {
    VendorDto createVendor(VendorRequest newVendor);

    VendorDto updateVendor(Integer id, VendorRequest updateVendor);

    HttpStatus deleteVendor(Integer id);

    VendorDto getVendorById(Integer id);

    Page<VendorDto> getAllVendor(int page, int pageSize);
}
