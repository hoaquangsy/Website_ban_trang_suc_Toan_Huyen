package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.VendorDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.VendorEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.VendorRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.VendorRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VendorDto createVendor(VendorRequest vendorRequest) {
        VendorEntity vendor = modelMapper.map(vendorRequest, VendorEntity.class);
        vendor.setVendorId(UUID.randomUUID());
        vendor.setDeleted(Boolean.FALSE);
        return modelMapper.map(vendorRepository.save(vendor), VendorDto.class);
    }

    @Override
    public VendorDto updateVendor(UUID id, VendorRequest updateVendor) {
        VendorEntity vendor = modelMapper.map(updateVendor, VendorEntity.class);
        VendorEntity vendorOpt = vendorRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Vendor not found")
        );
        vendor.setVendorId(id);
        vendor.setDeleted(Boolean.FALSE);
        return modelMapper.map(vendorRepository.save(vendor),VendorDto.class);
    }

    @Override
    public HttpStatus deleteVendor(UUID id) {
        VendorEntity vendor = vendorRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Vendor not found")
        );
        vendor.setDeleted(Boolean.TRUE);
        vendorRepository.save(vendor);
        return HttpStatus.OK;
    }

    @Override
    public VendorDto getVendorById(UUID id) {
        VendorEntity vendor = vendorRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Vendor not found")
        );
        return modelMapper.map(vendor,VendorDto.class);
    }

    @Override
    public PageDTO getAllVendor(Integer page, Integer pageSize, String keyword, String sortBy) {
        List<VendorEntity> categoryEntityList = this.vendorRepository.search(page,pageSize,keyword,sortBy);
        List<VendorDto> vendorDtos = categoryEntityList.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity,VendorDto.class)).collect(Collectors.toList());
        Long count = this.vendorRepository.count(page,pageSize,keyword,sortBy);
        return new PageDTO<>(vendorDtos,page,pageSize,count);
    }

    @Override
    public PageDTO autoComplete(Integer page, Integer pageSize, String keyword, String sortBy) {
        Long count = this.vendorRepository.count(page,pageSize,keyword,sortBy);
        pageSize = Integer.parseInt(count.toString());
        List<VendorEntity> categoryEntityList = this.vendorRepository.search(page,pageSize,keyword,sortBy);
        List<VendorDto> vendorDtos = categoryEntityList.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity,VendorDto.class)).collect(Collectors.toList());

        return new PageDTO<>(vendorDtos,page,pageSize,count);
    }

}
