package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.VendorDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.VendorEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.VendorRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.VendorRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class VendorServiceImpl implements VendorService {
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VendorDto createVendor(VendorRequest vendorRequest) {
        VendorEntity vendor = modelMapper.map(vendorRequest, VendorEntity.class);
        vendor.setCreateAt(new Date(System.currentTimeMillis()));
        System.out.println(vendorRequest.getBankName());
        return modelMapper.map(vendorRepository.save(vendor), VendorDto.class);
    }

    @Override
    public VendorDto updateVendor(Integer id, VendorRequest updateVendor) {
        VendorEntity vendor = vendorRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Vendor not found")
        );
        vendor.setLastModifiedAt(new Date(System.currentTimeMillis()));
        return modelMapper.map(vendorRepository.save(vendor),VendorDto.class);
    }

    @Override
    public HttpStatus deleteVendor(Integer id) {
        VendorEntity vendor = vendorRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Vendor not found")
        );
        vendorRepository.delete(vendor);
        return HttpStatus.OK;
    }

    @Override
    public VendorDto getVendorById(Integer id) {
        return null;
    }

    @Override
    public Page<VendorDto> getAllVendor(int page, int pageSize) {
        return null;
    }
}