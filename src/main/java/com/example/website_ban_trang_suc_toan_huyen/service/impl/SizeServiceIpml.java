package com.example.website_ban_trang_suc_toan_huyen.service.impl;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.SizeDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductSizeEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.SizeEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.SizeRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductSizeRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.SizeRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.SizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SizeServiceIpml implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ModelMapper mapper;

    @Override
    public SizeDto create(SizeRequest sizeRequest) {
        SizeEntity entity = this.mapper.map(sizeRequest,SizeEntity.class);
        entity.setDeleted(Boolean.FALSE);
        entity.setSizeId(UUID.randomUUID());
        return this.mapper.map( this.sizeRepository.save(entity),SizeDto.class);
    }

    @Override
    public SizeDto update(UUID id,SizeRequest sizeRequest) {
        SizeEntity entity =  this.sizeRepository.getSizeEntitiesBy(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Size not found"));
        entity.setSize(sizeRequest.getSize());
        entity.setDescription(sizeRequest.getDescription());
        return this.mapper.map( this.sizeRepository.save(entity),SizeDto.class);
    }

    @Override
    public SizeDto delete(UUID id) {
        SizeEntity entity =  this.sizeRepository.getSizeEntitiesBy(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Size not found"));
        entity.setDeleted(Boolean.TRUE);
        return this.mapper.map( this.sizeRepository.save(entity),SizeDto.class);
    }

    @Override
    public SizeDto getById(UUID id) {
        SizeEntity entity =  this.sizeRepository.getSizeEntitiesBy(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Size not found"));

        return this.mapper.map(entity,SizeDto.class);
    }

    @Override
    public List<SizeDto> getAllSize() {
        return this.sizeRepository.getAllBy().stream().
                map(sizeEntity -> this.mapper.map(sizeEntity,SizeDto.class)).collect(Collectors.toList());

    }

    @Override
    public List<SizeDto> getByProductId(UUID productId) {
        List<ProductSizeEntity> productSizeEntities = this.productSizeRepository.findByProductId(productId);

        List<UUID> sizeIds = productSizeEntities.stream().map(productSizeEntity -> productSizeEntity.getSizeId()).collect(Collectors.toList());

        List<SizeEntity> sizeEntities = this.sizeRepository.findAllById(sizeIds);
        return sizeEntities.stream().map(sizeEntity -> this.modelMapper.map(sizeEntity,SizeDto.class)).collect(Collectors.toList());
    }
}
