package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.VendorDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.VendorRequest;
import org.springframework.http.HttpStatus;

public interface AccessoryService {

    VendorDto createVendor(VendorRequest newVendor);

    VendorDto updateVendor(Integer id, VendorRequest updateVendor);

    HttpStatus deleteVendor(Integer id);

    VendorDto getVendorById(Integer id);

    PageDTO getAllVendor(Integer page, Integer pageSize, String keyword, String sortBy);
}
